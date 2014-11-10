package nci.obbr.cahub.datawarehouse



import org.junit.*
import grails.test.mixin.*

@TestFor(CaseDwController)
@Mock(CaseDw)
class CaseDwControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/caseDw/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.caseDwInstanceList.size() == 0
        assert model.caseDwInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.caseDwInstance != null
    }

    void testSave() {
        controller.save()

        assert model.caseDwInstance != null
        assert view == '/caseDw/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/caseDw/show/1'
        assert controller.flash.message != null
        assert CaseDw.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/caseDw/list'

        populateValidParams(params)
        def caseDw = new CaseDw(params)

        assert caseDw.save() != null

        params.id = caseDw.id

        def model = controller.show()

        assert model.caseDwInstance == caseDw
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/caseDw/list'

        populateValidParams(params)
        def caseDw = new CaseDw(params)

        assert caseDw.save() != null

        params.id = caseDw.id

        def model = controller.edit()

        assert model.caseDwInstance == caseDw
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/caseDw/list'

        response.reset()

        populateValidParams(params)
        def caseDw = new CaseDw(params)

        assert caseDw.save() != null

        // test invalid parameters in update
        params.id = caseDw.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/caseDw/edit"
        assert model.caseDwInstance != null

        caseDw.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/caseDw/show/$caseDw.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        caseDw.clearErrors()

        populateValidParams(params)
        params.id = caseDw.id
        params.version = -1
        controller.update()

        assert view == "/caseDw/edit"
        assert model.caseDwInstance != null
        assert model.caseDwInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/caseDw/list'

        response.reset()

        populateValidParams(params)
        def caseDw = new CaseDw(params)

        assert caseDw.save() != null
        assert CaseDw.count() == 1

        params.id = caseDw.id

        controller.delete()

        assert CaseDw.count() == 0
        assert CaseDw.get(caseDw.id) == null
        assert response.redirectedUrl == '/caseDw/list'
    }
}
