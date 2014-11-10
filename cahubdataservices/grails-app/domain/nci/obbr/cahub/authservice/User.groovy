package nci.obbr.cahub.authservice

class User {

    transient springSecurityService
    static transients = ['org'] //We want this to be transient and not persisting in DB. It is stubbed out here to be useful in the future.  See security filter for more.

    String email
    String fname
    String lname

    def org //Save for later: hidden from user admin screens, set during security filter based on authenticated role
    Date lastLoginDate //Save for later
    Date passwordChangeDate

    String username
    String password

    boolean enabled
    boolean accountLocked
    boolean accountExpired
    boolean passwordExpired

    static constraints = {
        username blank: false, unique: true
        password blank: false, nullable: false
        fname blank:true, nullable:true
        lname blank:true, nullable:true
        org blank:true, nullable:true
        lastLoginDate blank:true, nullable:true
        passwordChangeDate blank:true, nullable:true
    }

    static mapping = {
        table 'users'
        id generator:'sequence', params:[sequence:'users_pk']
        password column: '`password`'
    }

    Set<Role> getAuthorities() {
        UserRole.findAllByUser(this).collect { it.role } as Set
    }

    def beforeInsert() {
        encodePassword()
    }

    def beforeUpdate() {
        if(isDirty('password')) {
            encodePassword()
        }
    }

    protected void encodePassword() {
        password = springSecurityService.encodePassword(password)
    }
}
