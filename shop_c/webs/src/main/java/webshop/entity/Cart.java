package webshop.entity;

import javax.persistence.*;

@Entity
@Table(name = "Carts")
@IdClass(CartId.class)
public class Cart {
    @Id
    private int customersID;

    @Id
    private int productDetailsID;

    private Integer quantity;
    private Integer status;
    @Column(name = "total_price")
    private Integer totalPrice;

    @ManyToOne
    @JoinColumn(name = "customersID", insertable = false, updatable = false)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "product_detailsID", insertable = false, updatable = false)
    private ProductDetail productDetail;

    
	public Integer getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Integer totalPrice) {
		this.totalPrice = totalPrice;
	}

	public int getCustomersID() {
		return customersID;
	}

	public void setCustomersID(int customersID) {
		this.customersID = customersID;
	}

	public int getProductDetailsID() {
		return productDetailsID;
	}

	public void setProductDetailsID(int productDetailsID) {
		this.productDetailsID = productDetailsID;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public ProductDetail getProductDetail() {
		return productDetail;
	}

	public void setProductDetail(ProductDetail productDetail) {
		this.productDetail = productDetail;
	}

	public Cart() {
		super();
	}

	public Cart(int customersID, int productDetailsID, Integer quantity, Integer status, Integer totalPrice,
			Customer customer, ProductDetail productDetail) {
		super();
		this.customersID = customersID;
		this.productDetailsID = productDetailsID;
		this.quantity = quantity;
		this.status = status;
		this.totalPrice = totalPrice;
		this.customer = customer;
		this.productDetail = productDetail;
	}

	
}
