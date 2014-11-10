package nci.obbr.cahub.datarecords



import org.junit.*
import grails.test.mixin.*

@TestFor(DerivativeRecordController)
@Mock(DerivativeRecord)
class DerivativeRecordControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/derivativeRecord/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.derivativeRecordInstanceList.size() == 0
        assert model.derivativeRecordInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.derivativeRecordInstance != null
    }

    void testSave() {
        controller.save()

        assert model.derivativeRecordInstance != null
        assert view == '/derivativeRecord/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/derivativeRecord/show/1'
        assert controller.flash.message != null
        assert DerivativeRecord.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/derivativeRecord/list'

        populateValidParams(params)
        def derivativeRecord = new DerivativeRecord(params)

        assert derivativeRecord.save() != null

        params.id = derivativeRecord.id

        def model = controller.show()

        assert model.derivativeRecordInstance == derivativeRecord
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/derivativeRecord/list'

        populateValidParams(params)
        def derivativeRecord = new DerivativeRecord(params)

        assert derivativeRecord.save() != null

        params.id = derivativeRecord.id

        def model = controller.edit()

        assert model.derivativeRecordInstance == derivativeRecord
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/derivativeRecord/list'

        response.reset()

        populateValidParams(params)
        def derivativeRecord = new DerivativeRecord(params)

        assert derivativeRecord.save() != null

        // test invalid parameters in update
        params.id = derivativeRecord.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/derivativeRecord/edit"
        assert model.derivativeRecordInstance != null

        derivativeRecord.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/derivativeRecord/show/$derivativeRecord.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        derivativeRecord.clearErrors()

        populateValidParams(params)
        params.id = derivativeRecord.id
        params.version = -1
        controller.update()

        assert view == "/derivativeRecord/edit"
        assert model.derivativeRecordInstance != null
        assert model.derivativeRecordInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/derivativeRecord/list'

        response.reset()

        populateValidParams(params)
        def derivativeRecord = new DerivativeRecord(params)

        assert derivativeRecord.save() != null
        assert DerivativeRecord.count() == 1

        params.id = derivativeRecord.id

        controller.delete()

        assert DerivativeRecord.count() == 0
        assert DerivativeRecord.get(derivativeRecord.id) == null
        assert response.redirectedUrl == '/derivativeRecord/list'
    }
}
