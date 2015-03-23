package webapp.model.entities;

import java.io.Serializable;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the order database table.
 * 
 */
@Entity
@Table(name="order")
public class Order implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="order_id")
	private int orderId;

	@Column(name="autolock_time")
	private Timestamp autolockTime;

	@Column(name="is_locked")
	private int isLocked;

	private String status;

	//bi-directional many-to-one association to OrderItem
	@OneToMany(mappedBy="order")
	@JsonIgnore
	private List<OrderItem> orderItems;

	public Order() {
	}

	public int getOrderId() {
		return this.orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public Timestamp getAutolockTime() {
		return this.autolockTime;
	}

	public void setAutolockTime(Timestamp autolockTime) {
		this.autolockTime = autolockTime;
	}

	public boolean getIsLocked() {
		return this.isLocked != 0;
	}

	public void setIsLocked(boolean isLocked) {
		this.isLocked = isLocked ? 1 : 0;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<OrderItem> getOrderItems() {
		return this.orderItems;
	}

	public void setOrderItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}

	public OrderItem addOrderItem(OrderItem orderItem) {
		getOrderItems().add(orderItem);
		orderItem.setOrder(this);

		return orderItem;
	}

	public OrderItem removeOrderItem(OrderItem orderItem) {
		getOrderItems().remove(orderItem);
		orderItem.setOrder(null);

		return orderItem;
	}

}