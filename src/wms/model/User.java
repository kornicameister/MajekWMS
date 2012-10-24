package wms.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NaturalId;

import com.google.gson.annotations.Expose;

@Entity
@Table(name = "user", uniqueConstraints = { @UniqueConstraint(columnNames = { "login" }) })
public class User extends BaseEntity {
	@Transient
	private static final long serialVersionUID = -1852439527741996474L;

	@Id
	@Column(name = "idUser", updatable = false, insertable = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@GenericGenerator(name = "increment", strategy = "increment")
	@Expose
	protected Long id;

	@Basic
	@NaturalId
	@Column(name = "login", nullable = false, unique = true, length = 16, updatable = false)
	@Expose
	private String login;

	@Basic
	@Column(name = "password", nullable = false, unique = false, length = 45, updatable = true)
	private String password;

	public User() {
		super();
	}

	public synchronized final Long getId() {
		return id;
	}

	public synchronized final void setId(Long id) {
		this.id = id;
	}

	public synchronized final String getLogin() {
		return login;
	}

	public synchronized final void setLogin(String login) {
		this.login = login;
	}

	public synchronized final String getPassword() {
		return password;
	}

	public synchronized final void setPassword(String password) {
		this.password = password;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((login == null) ? 0 : login.hashCode());
		result = prime * result
				+ ((password == null) ? 0 : password.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof User))
			return false;
		User other = (User) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (login == null) {
			if (other.login != null)
				return false;
		} else if (!login.equals(other.login))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "User [getId()=" + getId() + ", getLogin()=" + getLogin()
				+ ", getPassword()=" + getPassword() + ", hashCode()="
				+ hashCode() + ", getVersion()=" + getVersion() + "]";
	}

}
