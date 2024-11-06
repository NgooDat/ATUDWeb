package webshop.entity;

import java.util.Collection;

import javax.persistence.*;
@Entity
@Table(name = "invoice_status")
public class InvoiceStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "invoiceStatus")
    private Collection<Invoice> invoices;

    
	public Collection<Invoice> getInvoices() {
		return invoices;
	}

	public void setInvoices(Collection<Invoice> invoices) {
		this.invoices = invoices;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public InvoiceStatus() {
		super();
	}

	public InvoiceStatus(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public InvoiceStatus(int id, String name, Collection<Invoice> invoices) {
		super();
		this.id = id;
		this.name = name;
		this.invoices = invoices;
	}

    
}

