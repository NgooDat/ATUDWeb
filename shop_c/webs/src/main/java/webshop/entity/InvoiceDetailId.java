package webshop.entity;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Embeddable;

@Embeddable
public class InvoiceDetailId implements Serializable {
    private int productDetailsID;
    private int invoicesID;
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
	@Override
	public int hashCode() {
		return Objects.hash(invoicesID, productDetailsID);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		InvoiceDetailId other = (InvoiceDetailId) obj;
		return invoicesID == other.invoicesID && productDetailsID == other.productDetailsID;
	}
	public InvoiceDetailId() {
		super();
	}
	public InvoiceDetailId(int productDetailsID, int invoicesID) {
		super();
		this.productDetailsID = productDetailsID;
		this.invoicesID = invoicesID;
	}

}
