package app.entities;

import java.io.Serializable;

import javax.persistence.*;

import java.util.List;


/**
 * The persistent class for the product database table.
 * 
 */
@Entity
@Table(name="product")
public class Product implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="product_id")
	private int productId;

	private String description;

	@Column(name="image_url")
	private String imageUrl;

	private String name;

	private int quantity;

	@Column(name="unit_rate")
	private float unitRate;

	//bi-directional many-to-one association to OrderItem
	@OneToMany(mappedBy="product")
	private List<OrderItem> orderItems;

	//bi-directional many-to-one association to ProductType
	@ManyToOne
	@JoinColumn(name="product_type_id")
	private ProductType productType;

	public Product() {
	}

	public Product(String name, ProductType productType, float unitRate, int quantity, String description,
			String imageUrl) {

		this.name = name;
		this.productType = productType;
		this.unitRate = unitRate;
		this.quantity = quantity;
		this.description = description;
		this.imageUrl = imageUrl;
	}

	public int getProductId() {
		return this.productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImageUrl() {
		return this.imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getQuantity() {
		return this.quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public float getUnitRate() {
		return this.unitRate;
	}

	public void setUnitRate(float unitRate) {
		this.unitRate = unitRate;
	}

	public List<OrderItem> getOrderItems() {
		return this.orderItems;
	}

	public void setOrderItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}

	public OrderItem addOrderItem(OrderItem orderItem) {
		getOrderItems().add(orderItem);
		orderItem.setProduct(this);

		return orderItem;
	}

	public OrderItem removeOrderItem(OrderItem orderItem) {
		getOrderItems().remove(orderItem);
		orderItem.setProduct(null);

		return orderItem;
	}

	public ProductType getProductType() {
		return this.productType;
	}

	public void setProductType(ProductType productType) {
		this.productType = productType;
	}

}
