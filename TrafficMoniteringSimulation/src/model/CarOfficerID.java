package model;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

@Embeddable
public class CarOfficerID implements java.io.Serializable
{

	private Car car;
    private Officer officer;

	@ManyToOne
	public Car getCar() {
		return car;
	}

	public void setCar(Car car) {
		this.car = car;
	}

	@ManyToOne
	public Officer getOfficer() {
		return officer;
	}

	public void setOfficer(Officer officer) {
		this.officer = officer;
	}

	public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CarOfficerID that = (CarOfficerID) o;

        if (car != null ? !car.equals(that.car) : that.car != null) return false;
        if (officer != null ? !officer.equals(that.officer) : that.officer != null)
            return false;

        return true;
    }

    public int hashCode() {
        int result;
        result = (car != null ? car.hashCode() : 0);
        result = 31 * result + (officer != null ? officer.hashCode() : 0);
        return result;
    }
    
}