package DTO;

public class PhieuXuatDTO {

    private String exportId;
    private String customer;
    private String customerId;
    private String customerAddress;
    private String customerPhone;
    private String customerEmail;
    private String employee;
    private String employeeId;
    private String employeePhone;
    private String employeeEmail;
    private String time;
    private String totalAmount;
    private String status;
    private Object[][] coffeeData;

    public PhieuXuatDTO(String exportId, String customer, String customerId, String customerAddress, String customerPhone, String customerEmail,
                        String employee, String employeeId, String employeePhone, String employeeEmail,
                        String time, String totalAmount, String status, Object[][] coffeeData) {
        this.exportId = exportId;
        this.customer = customer;
        this.customerId = customerId;
        this.customerAddress = customerAddress;
        this.customerPhone = customerPhone;
        this.customerEmail = customerEmail;
        this.employee = employee;
        this.employeeId = employeeId;
        this.employeePhone = employeePhone;
        this.employeeEmail = employeeEmail;
        this.time = time;
        this.totalAmount = totalAmount;
        this.status = status;
        this.coffeeData = coffeeData;
    }

    // Getter và Setter
    public String getExportId() {
        return exportId;
    }

    public void setExportId(String exportId) {
        this.exportId = exportId;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getEmployee() {
        return employee;
    }

    public void setEmployee(String employee) {
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object[][] getCoffeeData() {
        return coffeeData;
    }

    public void setCoffeeData(Object[][] coffeeData) {
        this.coffeeData = coffeeData;
    }

    public Object[] toTableRow(int stt) {
        return new Object[]{stt, exportId, customer, employee, time, totalAmount, status};
    }
}