package co.darma.forms;

import play.data.validation.Constraints.Required;

public class CompanyProfileForm {
	@Required
	public String employeeId;
	@Required
	public String addrFirstLine;
	public String addrSecondLine;
	@Required
	public String city;
	public String state;
	@Required
	public String country;
	@Required
	public Long companyId;
	@Required
	public String companyName;
	
	public String getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
	public String getAddrFirstLine() {
		return addrFirstLine;
	}
	public void setAddrFirstLine(String addrFirstLine) {
		this.addrFirstLine = addrFirstLine;
	}
	public String getAddrSecondLine() {
		return addrSecondLine;
	}
	public void setAddrSecondLine(String addrSecondLine) {
		this.addrSecondLine = addrSecondLine;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public Long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
}