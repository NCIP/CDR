package nci.obbr.cahub.datawarehouse



import org.junit.*
import grails.test.mixin.*

@TestFor(SpecimenDwController)
@Mock(SpecimenDw)
class SpecimenDwControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/specimenDw/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.specimenDwInstanceList.size() == 0
        assert model.specimenDwInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.specimenDwInstance != null
    }

    void testSave() {
        controller.save()

        assert model.specimenDwInstance != null
        assert view == '/specimenDw/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/specimenDw/show/1'
        assert controller.flash.message != null
        assert SpecimenDw.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/specimenDw/list'

        populateValidParams(params)
        def specimenDw = new SpecimenDw(params)

        assert specimenDw.save() != null

        params.id = specimenDw.id

        def model = controller.show()

        assert model.specimenDwInstance == specimenDw
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/specimenDw/list'

        populateValidParams(params)
        def specimenDw = new SpecimenDw(params)

        assert specimenDw.save() != null

        params.id = specimenDw.id

        def model = controller.edit()

        assert model.specimenDwInstance == specimenDw
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/specimenDw/list'

        response.reset()

        populateValidParams(params)
        def specimenDw = new SpecimenDw(params)

        assert specimenDw.save() != null

        // test invalid parameters in update
        params.id = specimenDw.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/specimenDw/edit"
        assert model.specimenDwInstance != null

        specimenDw.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/specimenDw/show/$specimenDw.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        specimenDw.clearErrors()

        populateValidParams(params)
        params.id = specimenDw.id
        params.version = -1
        controller.update()

        assert view == "/specimenDw/edit"
        assert model.specimenDwInstance != null
        assert model.specimenDwInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/specimenDw/list'

        response.reset()

        populateValidParams(params)
        def specimenDw = new SpecimenDw(params)

        assert specimenDw.save() != null
        assert SpecimenDw.count() == 1

        params.id = specimenDw.id

        controller.delete()

        assert SpecimenDw.count() == 0
        assert SpecimenDw.get(specimenDw.id) == null
        assert response.redirectedUrl == '/specimenDw/list'
    }
}
