package app.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;


/**
 * The persistent class for the bill_layout_settings database table.
 * 
 */
@Entity
@Table(name="bill_layout_settings")
public class BillLayoutSettings implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="bill_layout_settings_id")
	private int billLayoutSettingsId;

	@Column(name="header_content")
	private String headerContent;

	@Column(name="footer_content")
	private String footerContent;

	//bi-directional one-to-one association to Organization
	@OneToOne
	@JoinColumn(name="organization_id")
	private Organization organization;

	public BillLayoutSettings() {
	}

	public BillLayoutSettings(Organization organization, String headerContent, String footerContent) {
		this.organization = organization;
		this.headerContent = headerContent;
		this.footerContent = footerContent;
	}

	public int getBillLayoutSettingsId() {
		return this.billLayoutSettingsId;
	}

	public void setBillLayoutSettingsId(int billLayoutSettingsId) {
		this.billLayoutSettingsId = billLayoutSettingsId;
	}

	public Organization getOrganization() {
		return this.organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	public String getHeaderContent() {
		return this.headerContent;
	}

	public void setHeaderContent(String headerContent) {
		this.headerContent = headerContent;
	}

	public String getFooterContent() {
		return this.footerContent;
	}

	public void setFooterContent(String footerContent) {
		this.footerContent = footerContent;
	}

}
