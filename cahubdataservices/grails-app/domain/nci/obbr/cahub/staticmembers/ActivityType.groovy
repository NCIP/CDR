package nci.obbr.cahub.staticmembers

class ActivityType extends StaticMemberBaseClass {

    static mapping = {
        table 'st_activity_type'
        id generator:'sequence', params:[sequence:'st_activity_type_pk']
    }
}
