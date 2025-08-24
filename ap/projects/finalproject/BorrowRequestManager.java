package ap.projects.finalproject;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BorrowRequestManager {
    private List<BorrowRequest> borrowRequests;
    private BorrowRequestFileManager fileManager;

    public BorrowRequestManager() {
        this.fileManager = new BorrowRequestFileManager();
        this.borrowRequests = fileManager.loadBorrowRequests();
        if (this.borrowRequests == null) {
            this.borrowRequests = new ArrayList<>();
        }
    }

    public void createBorrowRequest(String studentUsername, String bookIsbn, LocalDate desiredBorrowDate) {
        boolean hasPendingRequest = borrowRequests.stream()
                .anyMatch(request -> request.getStudentUsername().equals(studentUsername) &&
                        request.getBookIsbn().equals(bookIsbn) &&
                        request.isPending());

        if (hasPendingRequest) {
            System.out.println("You already have a pending request for this book.");
            return;
        }

        BorrowRequest request = new BorrowRequest(studentUsername, bookIsbn, desiredBorrowDate);
        borrowRequests.add(request);
        saveRequests();
        System.out.println("Borrow request submitted successfully. Waiting for approval.");
    }

    public List<BorrowRequest> getPendingRequests() {
        return borrowRequests.stream()
                .filter(BorrowRequest::isPending)
                .collect(Collectors.toList());
    }

    public List<BorrowRequest> getPendingRequestsForToday() {
        return borrowRequests.stream()
                .filter(request -> request.isPending() && request.isForToday())
                .collect(Collectors.toList());
    }

    public List<BorrowRequest> getStudentRequests(String studentUsername) {
        return borrowRequests.stream()
                .filter(request -> request.getStudentUsername().equals(studentUsername))
                .collect(Collectors.toList());
    }

    public BorrowRequest findRequest(String studentUsername, String bookIsbn) {
        return borrowRequests.stream()
                .filter(request -> request.getStudentUsername().equals(studentUsername) &&
                        request.getBookIsbn().equals(bookIsbn) &&
                        request.isPending())
                .findFirst()
                .orElse(null);
    }

    public boolean approveRequest(String studentUsername, String bookIsbn) {
        BorrowRequest request = findRequest(studentUsername, bookIsbn);
        if (request != null) {
            request.approve();
            saveRequests();
            return true;
        }
        return false;
    }

    public boolean rejectRequest(String studentUsername, String bookIsbn, String reason) {
        BorrowRequest request = findRequest(studentUsername, bookIsbn);
        if (request != null) {
            request.reject(reason);
            saveRequests();
            return true;
        }
        return false;
    }

    public void displayPendingRequests() {
        List<BorrowRequest> pendingRequests = getPendingRequests();

        System.out.println("\n--- Pending Borrow Requests ---");
        if (pendingRequests.isEmpty()) {
            System.out.println("No pending borrow requests.");
            return;
        }

        pendingRequests.forEach(System.out::println);
    }

    public void displayPendingRequestsForToday() {
        List<BorrowRequest> todayRequests = getPendingRequestsForToday();

        System.out.println("\n--- Today's Pending Borrow Requests ---");
        if (todayRequests.isEmpty()) {
            System.out.println("No pending borrow requests for today.");
            return;
        }

        todayRequests.forEach(System.out::println);
    }

    private void saveRequests() {
        fileManager.saveBorrowRequests(borrowRequests);
    }
}