package com.kuzalab.veifyke


import Enviroment
import Verify
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.view.WindowManager
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.kuzalab.verifysdk.interfaces.*
import com.kuzalab.verifysdk.models.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.nca_response_view.view.*
import kotlinx.android.synthetic.main.person_response_view.view.*
import kotlinx.android.synthetic.main.recycler_view.view.*
import kotlinx.android.synthetic.main.verification_status_view.view.*


class MainActivity : AppCompatActivity(), TokenCallListener {
    override fun onTokenCallStarted() {

        setProgressBarVisibility(View.VISIBLE)
    }

    override fun onTokenRecieved(token: Token) {
        setProgressBarVisibility(View.GONE)
        Toast.makeText(this@MainActivity, "Token Recieved", Toast.LENGTH_LONG).show()

    }

    override fun onTokenCallFailed(verifyException: VerifyException) {
        setProgressBarVisibility(View.GONE)
        Toast.makeText(this@MainActivity, verifyException.errorMessage, Toast.LENGTH_LONG).show()

    }


    var v: Verify? = null
    private fun setProgressBarVisibility(visible: Int) {
        if (progress_bar != null) {
            progress_bar?.visibility = visible
        }

        if (visible == View.VISIBLE) {
            window?.setFlags(
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
            )
        } else {
            window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        v = Verify.Builder(this)
            .secretKey("tIpl3tw0agl9urNqianIAiYzPR5YnFMGriIad0qjcPq1c9HGsUuJhOkQRfZ5MuJY")
            .consumerKey("7Jp5N68ctdAtxqruKtQtFHmgLneY3S8Cz2iOKPtF4D5s715A1XWDo3oHHlEZ4Jgf")
            .enviroment(Enviroment.PRODUCTION)
            .handleTokenInternally(true, this)
            .build()

        setProgressBarVisibility(View.GONE)
        btn_search_person.setOnClickListener { launchDialog(DIALOGS.SEARCHPERSON) }
        btn_verify_person.setOnClickListener { launchDialog(DIALOGS.VERIFYPERSON) }
        btn_search_contactor_id.setOnClickListener { launchDialog(DIALOGS.SEARCHNCACONTRACTORID) }
        btn_search_contractor_name.setOnClickListener { launchDialog(DIALOGS.SEARCHNCACONTRACTORNAME) }
        btn_verify_contractor.setOnClickListener { launchDialog(DIALOGS.VERIFYNCACONTRACTOR) }

    }


    var edtIdNumber: EditText? = null
    var edtFristName: EditText? = null
    var edtSirName: EditText? = null
    var edtOtherName: EditText? = null
    var citizenship: EditText? = null
    var date_of_birth: EditText? = null
    var gender: EditText? = null
    var phone_number: EditText? = null


    var edtNcaContractorReg: EditText? = null
    var edtNcaContractorName: EditText? = null

    var edtNcaContractorTown: EditText? = null
    var edtNcaContractorCategory: EditText? = null
    var edtNcaContractorClass: EditText? = null


    private fun getLayout(dialog: DIALOGS): LinearLayout {
        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL

        when (dialog) {
            DIALOGS.SEARCHPERSON -> {
                edtIdNumber = EditText(this)
                edtIdNumber?.hint = "Person's ID number "
                edtIdNumber?.inputType = InputType.TYPE_CLASS_NUMBER
                layout.addView(edtIdNumber)

            }
            DIALOGS.VERIFYPERSON -> {
                edtIdNumber = EditText(this)
                edtIdNumber?.hint = "Person's ID number "

                edtIdNumber?.inputType = InputType.TYPE_CLASS_NUMBER
                layout.addView(edtIdNumber)

                edtFristName = EditText(this)
                edtFristName?.hint = "First Name "
                edtFristName?.inputType = InputType.TYPE_TEXT_VARIATION_PERSON_NAME
                layout.addView(edtFristName)


                edtSirName = EditText(this)
                edtSirName?.hint = "Person's Sir Name "
                edtSirName?.inputType = InputType.TYPE_TEXT_VARIATION_PERSON_NAME
                layout.addView(edtSirName)


                edtOtherName = EditText(this)
                edtOtherName?.hint = "Person's Other Name"
                edtOtherName?.inputType = InputType.TYPE_TEXT_VARIATION_PERSON_NAME
                layout.addView(edtOtherName)



                citizenship = EditText(this)
                citizenship?.hint = "Citizenship"
                citizenship?.inputType = InputType.TYPE_CLASS_TEXT
                layout.addView(citizenship)


                date_of_birth = EditText(this)
                date_of_birth?.hint = "Date Of Birth"
                date_of_birth?.inputType = InputType.TYPE_CLASS_DATETIME
                layout.addView(date_of_birth)

                gender = EditText(this)
                gender?.hint = "Gender"
                gender?.inputType = InputType.TYPE_CLASS_DATETIME
                layout.addView(gender)



                phone_number = EditText(this)
                phone_number?.hint = "Phone Number "
                phone_number?.inputType = InputType.TYPE_CLASS_DATETIME
                layout.addView(phone_number)


            }
            DIALOGS.SEARCHNCACONTRACTORID -> {
                edtNcaContractorReg = EditText(this)
                edtNcaContractorReg?.hint = "Contractors Registration Number (*)"
                edtNcaContractorReg?.inputType = InputType.TYPE_CLASS_TEXT
                layout.addView(edtNcaContractorReg)


            }

            DIALOGS.SEARCHNCACONTRACTORNAME -> {
                edtNcaContractorName = EditText(this)
                edtNcaContractorName?.hint = "Contractors Name (*)"
                edtNcaContractorName?.inputType = InputType.TYPE_CLASS_TEXT
                layout.addView(edtNcaContractorName)


            }

            DIALOGS.VERIFYNCACONTRACTOR -> {
                edtNcaContractorReg = EditText(this)
                edtNcaContractorReg?.hint = "Contractors Registration Number (*)"
                edtNcaContractorReg?.inputType = InputType.TYPE_CLASS_TEXT
                layout.addView(edtNcaContractorReg)


                edtNcaContractorName = EditText(this)
                edtNcaContractorName?.hint = "Contractors Name (*)"
                edtNcaContractorName?.inputType = InputType.TYPE_CLASS_TEXT
                layout.addView(edtNcaContractorName)

                edtNcaContractorTown = EditText(this)
                edtNcaContractorTown?.hint = "Contractors Town (*)"
                edtNcaContractorTown?.inputType = InputType.TYPE_CLASS_TEXT
                layout.addView(edtNcaContractorTown)

                edtNcaContractorCategory = EditText(this)
                edtNcaContractorCategory?.hint = "Contractors Category (*)"
                edtNcaContractorCategory?.inputType = InputType.TYPE_CLASS_TEXT
                layout.addView(edtNcaContractorCategory)

                edtNcaContractorClass = EditText(this)
                edtNcaContractorClass?.hint = "Contractor Class (*)"
                edtNcaContractorClass?.inputType = InputType.TYPE_CLASS_TEXT
                layout.addView(edtNcaContractorClass)


            }
        }

        return layout
    }

    private fun launchDialog(dialog: DIALOGS) {


        val builder = AlertDialog.Builder(this)
        builder.setTitle("")
        builder.setMessage("")
        builder.setView(getLayout(dialog))
        builder.setCancelable(false)

        builder.setPositiveButton("Complete") { d, which ->

            actOnAction(dialog)
        }

        builder.setNegativeButton("Cancel") { d, which ->

        }


        builder.show()


    }

    private fun actOnAction(dialog: DIALOGS?) {

        when (dialog) {
            DIALOGS.SEARCHPERSON -> {
                searchPerson(edtIdNumber?.text.toString())

            }
            DIALOGS.VERIFYPERSON -> {


                verifyPerson(
                    VerifyPersonodel(
                        id_number = edtIdNumber?.text.toString(),
                        first_name = edtFristName?.text.toString(),
                        surname = edtSirName?.text.toString(),
                        other_name = edtOtherName?.text.toString(),
                        phone_number = phone_number?.text.toString(),
                        gender = gender?.text.toString(),
                        citizenship = citizenship?.text.toString(),
                        date_of_birth = date_of_birth?.text.toString()
                    )
                )

            }
            DIALOGS.SEARCHNCACONTRACTORID -> {

                searchNcaContractorById(edtNcaContractorReg?.text.toString())

            }
            DIALOGS.SEARCHNCACONTRACTORNAME -> {

                searchNcaContractorByName(edtNcaContractorName?.text.toString())

            }
            DIALOGS.VERIFYNCACONTRACTOR -> {


                verifyNcaContractor(
                            VerifyNcaContractor(
                                edtNcaContractorReg?.text.toString(),
                                edtNcaContractorName?.text.toString(),
                                edtNcaContractorTown?.text.toString(),
                                edtNcaContractorCategory?.text.toString(),
                                edtNcaContractorClass?.text.toString()
                            )
                        )


            }
        }
    }


    private fun showError(edt: String) {
        Toast.makeText(this, "$edt  required", Toast.LENGTH_LONG).show()

    }

    private fun showVerificationResponseParams(paramsResponse: List<ParamsResponse>) {
        val builder = AlertDialog.Builder(this)

        builder.setTitle("")
        builder.setMessage("")
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.recycler_view, null)
        builder.setView(dialogView)
        builder.setCancelable(false)

        val adapter = VerificationParamsAdapter(paramsResponse)
        dialogView.recycler_view.adapter = adapter
        dialogView.recycler_view.layoutManager = LinearLayoutManager(this)


        builder.setNegativeButton("Cancel") { d, which ->

        }


        builder.show()
    }

    private fun showPersonResponseDialog(person: Person) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("")
        builder.setMessage("")
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.person_response_view, null)
        builder.setView(dialogView)
        builder.setCancelable(false)



        dialogView.id_number.text = person.idNumber
        dialogView.date_of_issue.text = person.dateOfIssue
        dialogView.first_name.text = person.firstName
        dialogView.surname.text = person.surname
        dialogView.other_name.text = person.otherName
        dialogView.dob.text = person.dateOfBirth
        dialogView.phone.text = person.phoneNumber
        dialogView.occupation.text = person.occupation
        dialogView.place_of_birth.text = person.placeOfBirth
        dialogView.place_of_live.text = person.placeOfLive

        dialogView.is_alive.text = person.isAlive.toString()
        dialogView.verified.text = person.verified.toString()
        dialogView.status.text = person.status





        builder.setNegativeButton("Cancel") { d, which ->

        }


        builder.show()

    }

    private fun showNcaContractorResponseDialog(ncaContractor: NcaContractor) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("")
        builder.setMessage("")

        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.nca_response_view, null)
        builder.setView(dialogView)
        builder.setCancelable(false)

        dialogView.registration_no.text = ncaContractor.registrationNo
        dialogView.contractor_name.text = ncaContractor.contractorName
        dialogView.town.text = ncaContractor.town
        dialogView.category.text = ncaContractor.category
        dialogView._class.text = ncaContractor._class

        dialogView.verified.text = ncaContractor.verified.toString()
        dialogView.status.text = ncaContractor.status







        builder.setNegativeButton("Cancel") { d, which ->

        }


        builder.show()

    }

    private fun showNcaContractorsResponseDialog(ncaContractors: List<NcaContractor>) {
        val builder = AlertDialog.Builder(this)

        builder.setTitle("Nca Contractors")
        builder.setMessage("")
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.recycler_view, null)
        builder.setView(dialogView)
        builder.setCancelable(false)

        val adapter = NcaParamsAdapter(ncaContractors)
        dialogView.recycler_view.adapter = adapter
        dialogView.recycler_view.layoutManager = LinearLayoutManager(this)


        builder.setNegativeButton("Cancel") { d, which ->

        }


        builder.show()

    }


    private fun searchPerson(personId: String) {
        v?.getPerson(personId, object : GetUserDetailsListener {
            override fun onCallStarted() {

                setProgressBarVisibility(View.VISIBLE)
            }

            override fun onResponse(person: Person) {
                setProgressBarVisibility(View.GONE)
                showPersonResponseDialog(person)
            }

            override fun onFailure(verifyException: VerifyException) {
                setProgressBarVisibility(View.GONE)
                Toast.makeText(this@MainActivity, verifyException.errorMessage, Toast.LENGTH_LONG).show()

            }
        })
    }


    private fun verifyPerson(verifyPersonodel: VerifyPersonodel) {
        v?.verifyPerson(verifyPersonodel, object : VerifyUserDetailsListener {
            override fun onCallStarted() {
                setProgressBarVisibility(View.VISIBLE)

            }

            override fun onResponse(paramsResponse: List<ParamsResponse>) {
                setProgressBarVisibility(View.GONE)
                showVerificationResponseParams(paramsResponse)

            }

            override fun onFailure(verifyException: VerifyException) {
                setProgressBarVisibility(View.GONE)
                Toast.makeText(this@MainActivity, verifyException.errorMessage, Toast.LENGTH_LONG).show()
            }
        })
    }


    private fun searchNcaContractorById(contractorRegId: String) {
        v?.searchNcaContractorById(contractorRegId, object : SearchNcaContractorByIdListener {
            override fun onCallStarted() {
                setProgressBarVisibility(View.VISIBLE)

            }

            override fun onResponse(
                ncaContractor: NcaContractor,
                verificationStatus: Boolean,
                verificationMessage: String
            ) {
                setProgressBarVisibility(View.GONE)
                showNcaContractorResponseDialog(ncaContractor)

            }

            override fun onFailure(verifyException: VerifyException) {
                setProgressBarVisibility(View.GONE)

                Toast.makeText(this@MainActivity, verifyException.errorMessage, Toast.LENGTH_LONG).show()

            }
        })
    }

    private fun searchNcaContractorByName(contractorName: String) {
        v?.searchNcaContractorByName(contractorName, object : SearchNcaContractorByNameListener {
            override fun onCallStarted() {
                setProgressBarVisibility(View.VISIBLE)

            }

            override fun onResponse(ncaContractor: List<NcaContractor>) {
                setProgressBarVisibility(View.GONE)
                showNcaContractorsResponseDialog(ncaContractor)


            }

            override fun onFailure(verifyException: VerifyException) {
                setProgressBarVisibility(View.GONE)

                Toast.makeText(this@MainActivity, verifyException.errorMessage, Toast.LENGTH_LONG).show()

            }
        })
    }


    private fun verifyNcaContractor(verifyNcaContractor: VerifyNcaContractor) {
        v?.verifyNcaContractor(verifyNcaContractor, object : verifyNcaContractorListener {
            override fun onCallStarted() {
                setProgressBarVisibility(View.VISIBLE)

            }

            override fun onResponse(paramsResponse: List<ParamsResponse>) {
                setProgressBarVisibility(View.GONE)
                showVerificationResponseParams(paramsResponse)


            }

            override fun onFailure(verifyException: VerifyException) {
                setProgressBarVisibility(View.GONE)
                Toast.makeText(this@MainActivity, verifyException.errorMessage, Toast.LENGTH_LONG).show()

            }
        })
    }

    enum class DIALOGS {
        SEARCHPERSON,
        VERIFYPERSON,
        SEARCHNCACONTRACTORID,
        SEARCHNCACONTRACTORNAME,
        VERIFYNCACONTRACTOR
    }

}
