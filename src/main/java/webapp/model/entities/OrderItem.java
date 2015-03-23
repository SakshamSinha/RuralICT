package webapp.model.entities;

import java.io.Serializable;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;


/**
 * The persistent class for the order_item database table.
 * 
 */
@Entity
@Table(name="order_item")
public class OrderItem implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="order_item_id")
	private int orderItemId;

	private int quantity;

	@Column(name="unit_rate")
	private float unitRate;

	//bi-directional many-to-one association to Order
	@ManyToOne
	@JoinColumn(name="order_id")
	private Order order;

	//bi-directional many-to-one association to Product
	@ManyToOne
	@JoinColumn(name="product_id")
	private Product product;

	public OrderItem() {
	}

	public OrderItem(Order order, Product product, int quantity, float unitRate) {
		this.order = order;
		this.product = product;
		this.quantity = quantity;
		this.unitRate = unitRate;
	}

	public int getOrderItemId() {
		return this.orderItemId;
	}

	public void setOrderItemId(int orderItemId) {
		this.orderItemId = orderItemId;
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

	@JsonProperty(value="orderId")
	@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="orderId")
	@JsonIdentityReference(alwaysAsId=true)
	public Order getOrder() {
		return this.order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	@JsonProperty(value="productId")
	@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="productId")
	@JsonIdentityReference(alwaysAsId=true)
	public Product getProduct() {
		return this.product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

}