package webshop.entity;


import javax.persistence.*;

@Entity
@Table(name = "invoice_details")
@IdClass(InvoiceDetailId.class)
public class InvoiceDetail {
    @Id
    private int productDetailsID;

    @Id
    private int invoicesID;

    private Integer quantity;
    private Integer unitPrice;

    @ManyToOne
    @JoinColumn(name = "product_detailsID", insertable = false, updatable = false)
    private ProductDetail productDetail;

    @ManyToOne
    @JoinColumn(name = "invoicesID", insertable = false, updatable = false)
    private Invoice invoice;

	public int getProductDetailsID() {
		return productDetailsID;
	}

	public void setProductDetailsID(int productDetailsID) {
		this.productDetailsID = productDetailsID;
	}

	public int getInvoicesID() {
		return invoicesID;
	}

	public void setInvoicesID(int invoicesID) {
		this.invoicesID = invoicesID;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Integer getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(Integer unitPrice) {
		this.unitPrice = unitPrice;
	}

	public ProductDetail getProductDetail() {
		return productDetail;
	}

	public void setProductDetail(ProductDetail productDetail) {
		this.productDetail = productDetail;
	}

	public Invoice getInvoice() {
		return invoice;
	}

	public void setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}

	public InvoiceDetail() {
		super();
	}

	public InvoiceDetail(int productDetailsID, int invoicesID, Integer quantity, Integer unitPrice,
			ProductDetail productDetail, Invoice invoice) {
		super();
		this.productDetailsID = productDetailsID;
		this.invoicesID = invoicesID;
		this.quantity = quantity;
		this.unitPrice = unitPrice;
		this.productDetail = productDetail;
		this.invoice = invoice;
	}

    // Getters and Setters
}


