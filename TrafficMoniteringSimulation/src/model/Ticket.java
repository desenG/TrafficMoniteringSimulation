package model;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
@Table(name = "Ticket")
@AssociationOverrides({
		@AssociationOverride(name = "k.car", joinColumns = @JoinColumn(name = "LICENSE")),
		@AssociationOverride(name = "k.officer", joinColumns = @JoinColumn(name = "OFFICER_ID")) })
public class Ticket implements java.io.Serializable
{
	private Integer ticketID;
	private CarOfficerID k = new CarOfficerID();
	private Date createdDate;
	private String ticketName;





	public Ticket() {
	}

	
	@Id
	@GeneratedValue
	@Column(name = "TICKET_ID")
	public Integer getTicketID() {
		return ticketID;
	}


	public void setTicketID(Integer ticketID) {
		this.ticketID = ticketID;
	}
	
	public CarOfficerID getK() {
		return k;
	}

	public void setK(CarOfficerID pk) {
		this.k = pk;
	}

	@Transient
	public Car getCar() {
		return getK().getCar();
	}

	public void setCar(Car car) {
		getK().setCar(car);
	}

	@Transient
	public Officer getOfficer() {
		return getK().getOfficer();
	}

	public void setOfficer(Officer officer) {
		getK().setOfficer(officer);
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "CREATED_DATE", nullable = false, length = 10)
	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getTicketName() {
		return ticketName;
	}


	public void setTicketName(String ticketName) {
		this.ticketName = ticketName;
	}


	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		Ticket that = (Ticket) o;

		if (getK() != null ? !getK().equals(that.getK())
				: that.getK() != null)
			return false;

		return true;
	}

	public int hashCode() {
		return (getK() != null ? getK().hashCode() : 0);
	}
}