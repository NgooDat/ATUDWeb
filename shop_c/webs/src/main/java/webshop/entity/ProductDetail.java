package webshop.entity;

import java.util.Collection;

import javax.persistence.*;
@Entity
@Table(name = "product_details")
public class ProductDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private int price;

    @ManyToOne
    @JoinColumn(name = "productsID", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "sizesID", nullable = false)
    private Size size;

    @OneToMany(mappedBy = "productDetail")
    private Collection<Cart> carts;

    @OneToMany(mappedBy = "productDetail")
    private Collection<InvoiceDetail> invoiceDetails;

	public Collection<Cart> getCarts() {
		return carts;
	}

	public void setCarts(Collection<Cart> carts) {
		this.carts = carts;
	}

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

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Size getSize() {
		return size;
	}

	public void setSize(Size size) {
		this.size = size;
	}

	public ProductDetail() {
		super();
	}

	public ProductDetail(int id, int quantity, int price, Product product, Size size) {
		super();
		this.id = id;
		this.quantity = quantity;
		this.price = price;
		this.product = product;
		this.size = size;
	}

	public ProductDetail(int id, int quantity, int price, Product product, Size size, Collection<Cart> carts,
			Collection<InvoiceDetail> invoiceDetails) {
		super();
		this.id = id;
		this.quantity = quantity;
		this.price = price;
		this.product = product;
		this.size = size;
		this.carts = carts;
		this.invoiceDetails = invoiceDetails;
	}

	
}
