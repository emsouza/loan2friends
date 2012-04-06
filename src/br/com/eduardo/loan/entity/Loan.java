package br.com.eduardo.loan.entity;

/**
 * @author Eduardo Matos de Souza<br>
 *         07/05/2011 <br>
 *         <a href="mailto:eduardomatosouza@gmail.com">eduardomatosouza@gmail.com</a>
 */
public class Loan {

	private Integer id;

	private Integer idFriend;

	private Integer idItem;

	private Integer status;

	private String date;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIdFriend() {
		return idFriend;
	}

	public void setIdFriend(Integer idFriend) {
		this.idFriend = idFriend;
	}

	public Integer getIdItem() {
		return idItem;
	}

	public void setIdItem(Integer idItem) {
		this.idItem = idItem;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getDate() {
		return date;
	}

}
