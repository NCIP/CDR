package nci.obbr.cahub.authservice

class Role {

	String authority

	static mapping = {
		cache true
                id generator:'sequence', params:[sequence:'role_pk']
	}

	static constraints = {
		authority blank: false, unique: true
	}
}
