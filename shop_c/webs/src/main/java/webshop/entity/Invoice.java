package webshop.entity;


import javax.persistence.*;

import java.util.Collection;
import java.util.Date;

@Entity
@Table(name = "invoices")
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @Column(name = "C_time", nullable = false)
    private Date createTime;

    @Column(name = "ship_fee", nullable = false)
    private int shipFee;

    @Column(name = "product_fee", nullable = false)
    private int productFee;

    @Column(name = "total", nullable = false)
    private int total;

    @Column(name = "payment_status", nullable = false)
    private int paymentStatus;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "payment_methodsID", nullable = false)
    private PaymentMethod paymentMethod;

    @ManyToOne
    @JoinColumn(name = "invoice_statusID", nullable = false)
    private InvoiceStatus invoiceStatus;

    @OneToMany(mappedBy = "invoice")
    private Collection<InvoiceDetail> invoiceDetails;

	public Collection<InvoiceDetail> getInvoiceDetails() {
		return invoiceDetails;
	}

	public void setInvoiceDetails(Collection<InvoiceDetail> invoiceDetails) {
		this.invoiceDetails = invoiceDetails;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public int getShipFee() {
		return shipFee;
	}

	public void setShipFee(int shipFee) {
		this.shipFee = shipFee;
	}

	public int getProductFee() {
		return productFee;
	}

	public void setProductFee(int productFee) {
		this.productFee = productFee;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(int paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public PaymentMethod getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(PaymentMethod paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public InvoiceStatus getInvoiceStatus() {
		return invoiceStatus;
	}

	public void setInvoiceStatus(InvoiceStatus invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
	}

	public Invoice() {
		super();
	}

	public Invoice(int id, Date createTime, int shipFee, int productFee, int total, int paymentStatus, String address,
			String description, PaymentMethod paymentMethod, InvoiceStatus invoiceStatus) {
		super();
		this.id = id;
		this.createTime = createTime;
		this.shipFee = shipFee;
		this.productFee = productFee;
		this.total = total;
		this.paymentStatus = paymentStatus;
		this.address = address;
		this.description = description;
		this.paymentMethod = paymentMethod;
		this.invoiceStatus = invoiceStatus;
	}

	public Invoice(int id, Date createTime, int shipFee, int productFee, int total, int paymentStatus, String address,
			String description, PaymentMethod paymentMethod, InvoiceStatus invoiceStatus,
			Collection<InvoiceDetail> invoiceDetails) {
		super();
		this.id = id;
		this.createTime = createTime;
		this.shipFee = shipFee;
		this.productFee = productFee;
		this.total = total;
		this.paymentStatus = paymentStatus;
		this.address = address;
		this.description = description;
		this.paymentMethod = paymentMethod;
		this.invoiceStatus = invoiceStatus;
		this.invoiceDetails = invoiceDetails;
	}

    
}

