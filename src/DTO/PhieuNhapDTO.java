package DTO;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class PhieuNhapDTO {

    private String receiptId;
    private String supplier;
    private String supplierId;
    private String supplierAddress;
    private String supplierPhone;
    private String supplierEmail;
    private String employee;
    private String employeeId;
    private String employeePhone;
    private String employeeEmail;
    private LocalDateTime time;
    private long totalAmount;
    private String status;
    private List<CoffeItemDTO> coffeeItems;

    public PhieuNhapDTO(String receiptId, String supplier, String supplierId, String supplierAddress, 
            String supplierPhone, String supplierEmail, String employee, String employeeId, 
            String employeePhone, String employeeEmail, LocalDateTime time, long totalAmount, 
            String status, List<CoffeItemDTO> coffeeItems) {
        validateRequiredFields(receiptId, supplier, employee, status);
        this.receiptId = receiptId;
        this.supplier = supplier;
        this.supplierId = supplierId;
        this.supplierAddress = supplierAddress;
        this.supplierPhone = supplierPhone;
        this.supplierEmail = supplierEmail;
        this.employee = employee;
        this.employeeId = employeeId;
        this.employeePhone = employeePhone;
        this.employeeEmail = employeeEmail;
        this.time = time;
        this.totalAmount = totalAmount;
        this.status = status;
        this.coffeeItems = coffeeItems;
    }

    private void validateRequiredFields(String receiptId, String supplier, String employee, String status) {
        if (receiptId == null || receiptId.trim().isEmpty()) {
            throw new IllegalArgumentException("Receipt ID cannot be null or empty");
        }
        if (supplier == null || supplier.trim().isEmpty()) {
            throw new IllegalArgumentException("Supplier cannot be null or empty");
        }
        if (employee == null || employee.trim().isEmpty()) {
            throw new IllegalArgumentException("Employee cannot be null or empty");
        }
        if (status == null || status.trim().isEmpty()) {
            throw new IllegalArgumentException("Status cannot be null or empty");
        }
    }

    public String getReceiptId() {
        return receiptId;
    }

    public void setReceiptId(String receiptId) {
        if (receiptId == null || receiptId.trim().isEmpty()) {
            throw new IllegalArgumentException("Receipt ID cannot be null or empty");
        }
        this.receiptId = receiptId;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        if (supplier == null || supplier.trim().isEmpty()) {
            throw new IllegalArgumentException("Supplier cannot be null or empty");
        }
        this.supplier = supplier;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupplierAddress() {
        return supplierAddress;
    }

    public void setSupplierAddress(String supplierAddress) {
        this.supplierAddress = supplierAddress;
    }

    public String getSupplierPhone() {
        return supplierPhone;
    }

    public void setSupplierPhone(String supplierPhone) {
        this.supplierPhone = supplierPhone;
    }

    public String getSupplierEmail() {
        return supplierEmail;
    }

    public void setSupplierEmail(String supplierEmail) {
        this.supplierEmail = supplierEmail;
    }

    public String getEmployee() {
        return employee;
    }

    public void setEmployee(String employee) {
        if (employee == null || employee.trim().isEmpty()) {
            throw new IllegalArgumentException("Employee cannot be null or empty");
        }
        this.employee = employee;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeePhone() {
        return employeePhone;
    }

    public void setEmployeePhone(String employeePhone) {
        this.employeePhone = employeePhone;
    }

    public String getEmployeeEmail() {
        return employeeEmail;
    }

    public void setEmployeeEmail(String employeeEmail) {
        this.employeeEmail = employeeEmail;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public long getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(long totalAmount) {
        if (totalAmount < 0) {
            throw new IllegalArgumentException("Total amount cannot be negative");
        }
        this.totalAmount = totalAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        if (status == null || status.trim().isEmpty()) {
            throw new IllegalArgumentException("Status cannot be null or empty");
        }
        this.status = status;
    }

    public List<CoffeItemDTO> getCoffeeItems() {
        return coffeeItems;
    }

    public void setCoffeeItems(List<CoffeItemDTO> coffeeItems) {
        this.coffeeItems = coffeeItems;
    }

    public Object[] toTableRow(int stt) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String formattedTime = time != null ? time.format(formatter) : "";
        String formattedAmount = String.format("%,dđ", totalAmount);
        return new Object[]{stt, receiptId, supplier, employee, formattedTime, formattedAmount, status};
    }
}