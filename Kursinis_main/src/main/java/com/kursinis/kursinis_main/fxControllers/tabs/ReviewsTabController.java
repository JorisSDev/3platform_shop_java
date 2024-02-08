package com.kursinis.kursinis_main.fxControllers.tabs;

import com.kursinis.kursinis_main.hibernateControllers.CustomHib;
import com.kursinis.kursinis_main.model.*;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.List;

public class ReviewsTabController {

    public ListView deliveredProductsList;
    public Slider ratingField;
    public TextField commentTitleField;
    public TextArea commentBodyField;
    public TreeView commentsTree;
    public Button reviewButton;

    private User currentUser;
    private CustomHib customHib;

    public void loadTabData(CustomHib customHib, User currentUser) {
        this.customHib = customHib;
        this.currentUser = currentUser;
        loadDeliveredProductsList();
        disableAllFields();
        limitAccess();
    }

    public void limitAccess() {
        if (currentUser instanceof Manager) {
            ratingField.setVisible(false);
            reviewButton.setVisible(false);
        }
    }

    public void disableAllFields() {
        commentTitleField.setDisable(true);
        commentBodyField.setDisable(true);
        ratingField.setDisable(true);
    }

    public void enableReviewFields() {
        enableReplyFields();
        ratingField.setDisable(false);
    }

    public void enableReplyFields() {
        ratingField.setDisable(true);
        commentTitleField.setDisable(false);
        commentBodyField.setDisable(false);
    }

    public void loadDeliveredProductsList() {
        deliveredProductsList.getItems().clear();

        List<Product> deliveredProducts = customHib.getDeliveredProducts(currentUser);
        if (deliveredProducts == null) {
            deliveredProducts = new ArrayList<>();
        }
        deliveredProductsList.getItems().addAll(deliveredProducts);
    }

    public void leaveReview(ActionEvent actionEvent) {
        // Get the selected product
        Product selectedProduct = (Product) deliveredProductsList.getSelectionModel().getSelectedItem();

        if (selectedProduct != null) {
            //check if the user has already left a review for this product
            // Create a new review
            Review review = new Review();
            review.setRating((int) ratingField.getValue());
            review.setCommentTitle(commentTitleField.getText());
            review.setCommentBody(commentBodyField.getText());
            review.setProduct(selectedProduct);

            // Save the review to the database
            customHib.create(review);

            // Clear the review fields
            commentTitleField.clear();
            commentBodyField.clear();
            ratingField.setValue(0);
            disableAllFields();
        }
    }

    public void loadSelectedProduct(MouseEvent mouseEvent) {
        enableReviewFields();
        loadCommentsTree();
    }

    public void replyToComment(ActionEvent actionEvent) {
        // Get the selected comment
        TreeItem<Comment> selectedComment = (TreeItem<Comment>) commentsTree.getSelectionModel().getSelectedItem();

        if (selectedComment != null) {
            // Create a new comment
            Comment comment = new Comment();
            comment.setCommentTitle(commentTitleField.getText());
            comment.setCommentBody(commentBodyField.getText());
            comment.setParentComment(selectedComment.getValue());

            // Save the comment to the database
            customHib.create(comment);

            // Clear the comment fields
            commentTitleField.clear();
            commentBodyField.clear();

            // Reload the comments tree
            loadCommentsTree();
        }
    }

    public void loadCommentsTree() {
        // Get the selected product
        Product selectedProduct = (Product) deliveredProductsList.getSelectionModel().getSelectedItem();

        if (selectedProduct != null) {
            // Load the commentsTree for the selected product
            commentsTree.setRoot(new TreeItem<>());
            commentsTree.setShowRoot(true); // Show the root of the tree
            commentsTree.getRoot().setExpanded(true);

            // Get the review for the selected product
            Review review = customHib.getReview(selectedProduct);
            if (review != null) {
                // Add the review to the root of the tree
                TreeItem<Comment> reviewTreeItem = new TreeItem<>(review);
                commentsTree.getRoot().getChildren().add(reviewTreeItem);
                reviewTreeItem.setExpanded(true); // Expand the review item

                // Load the comments for the review
                List<Comment> comments = customHib.getComments(review);
                if (comments != null) {
                    for (Comment comment : comments) {
                        TreeItem<Comment> commentTreeItem = new TreeItem<>(comment);
                        reviewTreeItem.getChildren().add(commentTreeItem);
                        commentTreeItem.setExpanded(true); // Expand the comment item
                        loadReplies(commentTreeItem);
                    }
                }
            }
        }
    }

    private void loadReplies(TreeItem<Comment> parentCommentTreeItem) {
        List<Comment> replies = customHib.getReplies(parentCommentTreeItem.getValue());
        for (Comment reply : replies) {
            TreeItem<Comment> replyTreeItem = new TreeItem<>(reply);
            parentCommentTreeItem.getChildren().add(replyTreeItem);
            replyTreeItem.setExpanded(true); // Expand the reply item
            loadReplies(replyTreeItem);
        }
    }


}
