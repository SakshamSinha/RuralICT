package app.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


/**
 * The persistent class for the preset_quantity database table.
 * 
 */
@Entity
@Table(name="preset_quantity")
public class PresetQuantity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="preset_quantity_id")
	private int presetQuantityId;

	private float quantity;

	//bi-directional many-to-one association to Organization
	@ManyToOne
	@JoinColumn(name="organization_id")
	private Organization organization;

	//bi-directional many-to-one association to ProductType
	@ManyToOne
	@JoinColumn(name="product_type_id")
	private ProductType productType;

	public PresetQuantity() {
	}

	public PresetQuantity(Organization organization, ProductType productType, float quantity) {
		this.organization = organization;
		this.productType = productType;
		this.quantity = quantity;
	}

	public int getPresetQuantityId() {
		return this.presetQuantityId;
	}

	public void setPresetQuantityId(int presetQuantityId) {
		this.presetQuantityId = presetQuantityId;
	}

	public float getQuantity() {
		return this.quantity;
	}

	public void setQuantity(float quantity) {
		this.quantity = quantity;
	}

	public Organization getOrganization() {
		return this.organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	public ProductType getProductType() {
		return this.productType;
	}

	public void setProductType(ProductType productType) {
		this.productType = productType;
	}

}
