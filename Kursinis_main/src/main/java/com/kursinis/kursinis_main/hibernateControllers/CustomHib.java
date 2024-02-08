package com.kursinis.kursinis_main.hibernateControllers;

import com.kursinis.kursinis_main.model.*;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import javafx.scene.chart.PieChart;

import java.util.ArrayList;
import java.util.logging.Logger;


import java.util.List;

public class CustomHib extends GenericHib {
    private static final Logger LOGGER = Logger.getLogger(CustomHib.class.getName());

    public CustomHib(EntityManagerFactory entityManagerFactory) {
        super(entityManagerFactory);
    }

    public User getUserByCredentials(String login, String password) {
        EntityManager em = getEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<User> query = cb.createQuery(User.class);
            Root<User> root = query.from(User.class);
            query.select(root).where(cb.and(cb.like(root.get("login"), login), cb.like(root.get("password"), password)));
            Query q;

            q = em.createQuery(query);
            return (User) q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            if (em != null) em.close();
        }
    }

    public void deleteProduct(int id) {

        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            var product = em.find(Product.class, id);
            //Kai turiu objekta, galiu ji "nulinkint"

            Warehouse warehouse = product.getWarehouse();
            if (warehouse != null) {
                warehouse.getInStockProducts().remove(product);
                em.merge(warehouse);
            }

            em.remove(product);
            em.getTransaction().commit();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Product> getAvailableProducts() {
        EntityManager em = getEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Product> query = cb.createQuery(Product.class);
            Root<Product> root = query.from(Product.class);
            query.select(root).where(cb.isNull(root.get("cart")));
            Query q;

            q = em.createQuery(query);
            return q.getResultList();
        } catch (NoResultException e) {
            return null;
        } finally {
            if (em != null) em.close();
        }
    }

    public void addToCart(int userId, List<Product> productList) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();

            User user = getEntityById(User.class, userId);
            Cart cart = new Cart(user);
            for (Product p : productList) {
                Product product = getEntityById(Product.class, p.getId());
                product.setCart(cart);
                cart.getItemsInCart().add(product);
            }
            cart.setStatus("pending");
            user.getMyCarts().add(cart);
            em.merge(user);

            em.getTransaction().commit();
        } catch (NoResultException e) {
            e.printStackTrace();
        } finally {
            if (em != null) em.close();
        }
    }

    public void deleteComment(int id) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            var comment = em.find(Comment.class, id);

            if (comment.getClass() == Review.class) {
                Review review = (Review) comment;
                Product product = em.find(Product.class, review.getProduct().getId());
                product.getReviews().remove(review);
                em.merge(product);
            } else {
                comment.getReplies().clear();
                em.remove(comment);

            }
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Product> getProductsByCartId(int cartId) {
        EntityManager em = getEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Product> query = cb.createQuery(Product.class);
            Root<Product> root = query.from(Product.class);

            query.select(root).where(cb.equal(root.get("cart").get("id"), cartId));

            TypedQuery<Product> typedQuery = em.createQuery(query);

            return typedQuery.getResultList();

        } catch (Exception e) {
            return null;
        } finally {
            if (em != null) em.close();
        }
    }

    public List<Product> getProductsNotInCart() {
        EntityManager em = getEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Product> query = cb.createQuery(Product.class);
            Root<Product> root = query.from(Product.class);

            query.select(root).where(cb.isNull(root.get("cart")));

            TypedQuery<Product> typedQuery = em.createQuery(query);

            return typedQuery.getResultList();

        } catch (Exception e) {
            return null;
        } finally {
            if (em != null) em.close();
        }
    }

    public List<Cart> getFilteredOrders(String status, Customer customer, Integer productCount) {
        EntityManager em = getEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Cart> query = cb.createQuery(Cart.class);
            Root<Cart> root = query.from(Cart.class);

            Predicate predicate = cb.conjunction();

            if (customer != null) {
                Integer ownerId = customer.getId();
                predicate = cb.and(predicate, cb.equal(root.get("owner"), customer));
                LOGGER.info("Customer filter applied: " + customer);
            }

            if (status != null) {
                predicate = cb.and(predicate, cb.equal(root.get("status"), status));
                LOGGER.info("Status filter applied: " + status);
            }

            if (productCount != 0) {
                predicate = cb.and(predicate, cb.equal(cb.size(root.get("itemsInCart")), productCount));
                LOGGER.info("Product count filter applied: " + productCount);
            }


            query.where(predicate);

            TypedQuery<Cart> typedQuery = em.createQuery(query);
            List<Cart> resultList = typedQuery.getResultList();
            LOGGER.info("Number of results: " + resultList.size());

            return resultList;

        } catch (Exception e) {
            LOGGER.severe("Error while filtering orders: " + e.getMessage());
            return null;
        } finally {
            if (em != null) em.close();
        }
    }

    public List<Cart> getOrdersByCustomerId(int id) {
        EntityManager em = getEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Cart> query = cb.createQuery(Cart.class);
            Root<Cart> root = query.from(Cart.class);

            query.select(root).where(cb.equal(root.get("owner").get("id"), id));

            TypedQuery<Cart> typedQuery = em.createQuery(query);

            return typedQuery.getResultList();

        } catch (Exception e) {
            return null;
        } finally {
            if (em != null) em.close();
        }
    }

    public List<PieChart.Data> getOrderStatusPieChartData(Customer selectedCustomer) {
        EntityManager em = getEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Long> query = cb.createQuery(Long.class);
            Root<Cart> root = query.from(Cart.class);

            List<PieChart.Data> pieChartData = new ArrayList<>();

            for (String status : new String[]{"confirmed", "pending", "cancelled", "delivered"}) {
                if (selectedCustomer != null) {
                    query.select(cb.count(root)).where(cb.and(cb.equal(root.get("status"), status), cb.equal(root.get("owner"), selectedCustomer)));
                } else {
                    query.select(cb.count(root)).where(cb.equal(root.get("status"), status));
                }
                TypedQuery<Long> typedQuery = em.createQuery(query);
                pieChartData.add(new PieChart.Data(status, typedQuery.getSingleResult()));
            }

            return pieChartData;

        } catch (Exception e) {
            return null;
        } finally {
            if (em != null) em.close();
        }
    }

    public long getTotalItemsSold(Customer selectedCustomer) {
        EntityManager em = getEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Long> query = cb.createQuery(Long.class);
            Root<Product> root = query.from(Product.class);

            if (selectedCustomer != null) {
                query.select(cb.count(root)).where(cb.and(cb.isNotNull(root.get("cart")), cb.equal(root.get("cart").get("owner"), selectedCustomer)));
            } else {
                query.select(cb.count(root)).where(cb.isNotNull(root.get("cart")));
            }
            TypedQuery<Long> typedQuery = em.createQuery(query);

            return typedQuery.getSingleResult();

        } catch (Exception e) {
            return 0;
        } finally {
            if (em != null) em.close();
        }
    }

    public List<Product> getDeliveredProducts(User user) {
        if (user instanceof Manager) {
            List<Cart> allCarts = getAllRecords(Cart.class);
            List<Product> deliveredProducts = new ArrayList<>();
            for (Cart cart : allCarts) {
                deliveredProducts.addAll(getProductsByCartId(cart.getId()));
            }
            return deliveredProducts;
        } else if (user instanceof Customer) {
            List<Cart> userCarts = getOrdersByCustomerId(user.getId());
            List<Product> deliveredProducts = new ArrayList<>();
            for (Cart cart : userCarts) {
                deliveredProducts.addAll(getProductsByCartId(cart.getId()));
            }
            return deliveredProducts;
        }
        return new ArrayList<>();
    }


    public Object getCommentsTree(Product selectedProduct) {
        EntityManager em = getEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Comment> query = cb.createQuery(Comment.class);
            Root<Comment> root = query.from(Comment.class);

            query.select(root).where(cb.equal(root.get("product"), selectedProduct), cb.isNull(root.get("parentComment")));

            TypedQuery<Comment> typedQuery = em.createQuery(query);
            List<Comment> resultList = typedQuery.getResultList();
            return resultList;

        } catch (Exception e) {
            return null;
        } finally {
            if (em != null) em.close();
        }
    }

    public Review getReview(Product selectedProduct) {
        EntityManager em = getEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Review> query = cb.createQuery(Review.class);
            Root<Review> root = query.from(Review.class);

            query.select(root).where(cb.equal(root.get("product"), selectedProduct));

            TypedQuery<Review> typedQuery = em.createQuery(query);
            return typedQuery.getSingleResult();

        } catch (Exception e) {
            return null;
        } finally {
            if (em != null) em.close();
        }
    }

    public List<Comment> getComments(Review review) {
        EntityManager em = getEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Comment> query = cb.createQuery(Comment.class);
            Root<Comment> root = query.from(Comment.class);

            // Select comments where the parentComment is the given review
            query.select(root).where(cb.equal(root.get("parentComment"), review));

            TypedQuery<Comment> typedQuery = em.createQuery(query);
            return typedQuery.getResultList();

        } catch (Exception e) {
            return null;
        } finally {
            if (em != null) em.close();
        }
    }

    public List<Comment> getReplies(Comment parentComment) {
        EntityManager em = getEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Comment> query = cb.createQuery(Comment.class);
            Root<Comment> root = query.from(Comment.class);

            // Select comments where the parentComment is the given comment
            query.select(root).where(cb.equal(root.get("parentComment"), parentComment));

            TypedQuery<Comment> typedQuery = em.createQuery(query);
            return typedQuery.getResultList();

        } catch (Exception e) {
            return null;
        } finally {
            if (em != null) em.close();
        }
    }
}
