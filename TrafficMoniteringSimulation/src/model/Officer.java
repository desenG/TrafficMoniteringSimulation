package model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "officer")
public class Officer implements java.io.Serializable
{

	private String officerId;
	private String officerName;
	private String region;
	private String status;
	private Set<Ticket> tickets = new HashSet<Ticket>(0);
	
	public Officer()
	{
		
	}
	public Officer(String officerId, String officerName, String region, Set<Ticket> tickets, String status) {
		
		this.officerId=officerId;
		this.officerName=officerName;
		this.region=region;
		this.tickets=tickets;
		this.status=status;
	}

	public Officer(String officerId) {
		this.officerId = officerId;
	}

	public Officer(String officerName, Set<Ticket> tickets) {
		this.officerName = officerName;
		this.tickets = tickets;
	}

	public Officer(String officerId, String region) {
		this(officerId);
		this.region=region;
	}

	@Id
	@Column(name = "OFFICER_ID")
	public String getOfficerId() {
		return this.officerId;
	}

	public void setOfficerId(String officerId) {
		this.officerId = officerId;
	}

	@Column(name = "OFFICER_NAME", nullable = true, length = 100)
	public String getOfficerName() {
		return this.officerName;
	}

	public void setOfficerName(String officerName) {
		this.officerName = officerName;
	}


	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "k.officer", cascade=CascadeType.ALL)
	public Set<Ticket> getTickets() {
		return this.tickets;
	}

	public void setTickets(Set<Ticket> tickets) {
		this.tickets = tickets;
	}
	

}
