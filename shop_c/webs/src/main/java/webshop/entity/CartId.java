package webshop.entity;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Embeddable;

@Embeddable
public class CartId implements Serializable {
    private int customersID;
    private int productDetailsID;
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
	@Override
	public int hashCode() {
		return Objects.hash(customersID, productDetailsID);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CartId other = (CartId) obj;
		return customersID == other.customersID && productDetailsID == other.productDetailsID;
	}
	public CartId() {
		super();
	}
	public CartId(int customersID, int productDetailsID) {
		super();
		this.customersID = customersID;
		this.productDetailsID = productDetailsID;
	}

}
