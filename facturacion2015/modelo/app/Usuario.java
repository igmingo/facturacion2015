package app;

public class Usuario {
	//table usuarios
	//*id, nombre, *password, estado, *administrador, *email
	
	private int id;
	private String name;
	private String email;
	private boolean isAdmin;
	private int status;
	
	//PROPIEDAD QUE INDICA SI EL USUARIO SE HA LOGEADO
	private boolean Loged;
	
	public Usuario(int id) {
		this.id = id;
	}

	public Usuario(int id, String username, String email,
			boolean isAdmin, int status) {
		this.id = id;
		this.name = username;
		this.email = email;
		this.isAdmin = isAdmin;
		this.status = status;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public boolean isLoged() {
		return Loged;
	}

	public void setLoged(boolean loged) {
		Loged = loged;
	}
	
}
