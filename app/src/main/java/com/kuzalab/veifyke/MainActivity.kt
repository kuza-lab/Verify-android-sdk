/*
 * *
 *  * Created by Kogi Eric  on 5/17/19 4:11 PM
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 5/17/19 4:10 PM
 *
 */

package com.kuzalab.veifyke


import Enviroment
import Verify
import android.annotation.SuppressLint
import android.app.DatePickerDialog
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
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private var edtIdNumber: EditText? = null
    private var edtFristName: EditText? = null
    private var edtSirName: EditText? = null
    private var edtOtherName: EditText? = null
    private var edtCitizenship: EditText? = null
    private var edtDateOfBirth: EditText? = null
    private var edtGender: EditText? = null
    private var edtPhoneNumber: EditText? = null
    private var edtNcaContractorReg: EditText? = null
    private var edtNcaContractorName: EditText? = null
    private var edtNcaContractorTown: EditText? = null
    private var edtNcaContractorCategory: EditText? = null
    private var edtNcaContractorClass: EditText? = null

    private var v: Verify? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        v = Verify.Builder(this)
            .secretKey("tIpl3tw0agl9urNqianIAiYzPR5YnFMGriIad0qjcPq1c9HGsUuJhOkQRfZ5MuJY")
            .consumerKey("7Jp5N68ctdAtxqruKtQtFHmgLneY3S8Cz2iOKPtF4D5s715A1XWDo3oHHlEZ4Jgf")
            .enviroment(Enviroment.PRODUCTION)
            .build()







        setProgressBarVisibility(View.GONE)
        btn_search_person.setOnClickListener { launchDialog(DIALOGS.SEARCHPERSON) }
        btn_verify_person.setOnClickListener { launchDialog(DIALOGS.VERIFYPERSON) }
        btn_search_contactor_id.setOnClickListener { launchDialog(DIALOGS.SEARCHNCACONTRACTORID) }
        btn_search_contractor_name.setOnClickListener { launchDialog(DIALOGS.SEARCHNCACONTRACTORNAME) }
        btn_verify_contractor.setOnClickListener { launchDialog(DIALOGS.VERIFYNCACONTRACTOR) }

    }

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



                edtCitizenship = EditText(this)
                edtCitizenship?.hint = "Citizenship"
                edtCitizenship?.inputType = InputType.TYPE_CLASS_TEXT
                layout.addView(edtCitizenship)


                edtDateOfBirth = EditText(this)
                edtDateOfBirth?.hint = "Date Of Birth"
                edtDateOfBirth?.isFocusable = false
                edtDateOfBirth?.isFocusableInTouchMode = false
                edtDateOfBirth?.setOnClickListener { chooseDob() }
                edtDateOfBirth?.inputType = InputType.TYPE_CLASS_TEXT
                layout.addView(edtDateOfBirth)

                edtGender = EditText(this)
                edtGender?.hint = "Gender"
                edtGender?.isFocusable = false
                edtGender?.isFocusableInTouchMode = false
                edtGender?.setOnClickListener { chooseGender() }
                edtGender?.inputType = InputType.TYPE_CLASS_TEXT
                layout.addView(edtGender)



                edtPhoneNumber = EditText(this)
                edtPhoneNumber?.hint = "Phone Number "
                edtPhoneNumber?.inputType = InputType.TYPE_CLASS_DATETIME
                layout.addView(edtPhoneNumber)


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
                edtNcaContractorReg?.hint = "Contractors Registration Number "
                edtNcaContractorReg?.inputType = InputType.TYPE_CLASS_TEXT
                layout.addView(edtNcaContractorReg)


                edtNcaContractorName = EditText(this)
                edtNcaContractorName?.hint = "Contractors Name "
                edtNcaContractorName?.inputType = InputType.TYPE_CLASS_TEXT
                layout.addView(edtNcaContractorName)

                edtNcaContractorTown = EditText(this)
                edtNcaContractorTown?.hint = "Contractors Town "
                edtNcaContractorTown?.inputType = InputType.TYPE_CLASS_TEXT
                layout.addView(edtNcaContractorTown)

                edtNcaContractorCategory = EditText(this)
                edtNcaContractorCategory?.hint = "Contractors Category "
                edtNcaContractorCategory?.inputType = InputType.TYPE_CLASS_TEXT
                layout.addView(edtNcaContractorCategory)

                edtNcaContractorClass = EditText(this)
                edtNcaContractorClass?.hint = "Contractor Class "
                edtNcaContractorClass?.inputType = InputType.TYPE_CLASS_TEXT
                layout.addView(edtNcaContractorClass)


            }
        }

        return layout
    }

    private fun chooseGender() {
        lateinit var dialog: AlertDialog
        val array = v?.VERIFY_GENDER_ARRAY
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Choose a Gender.")
        builder.setSingleChoiceItems(array, -1) { _, which ->
            edtGender?.setText(array!![which])
            dialog.dismiss()
        }
        dialog = builder.create()
        dialog.show()

    }

    private fun chooseDob() {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        val cal = Calendar.getInstance()
        val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, yeard, monthOfYear, dayOfMonth ->
            cal.set(Calendar.YEAR, yeard)
            cal.set(Calendar.MONTH, monthOfYear)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            val sdf = SimpleDateFormat(v?.VERIFY_DATE_FORMAT, Locale.US)
            edtDateOfBirth?.setText(sdf.format(cal.time))
        }, year, month, day)
        dpd.datePicker.maxDate = Date().time
        dpd.show()

    }

    private fun launchDialog(dialog: DIALOGS) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("")
        builder.setMessage("")
        builder.setView(getLayout(dialog))
        builder.setCancelable(false)
        builder.setPositiveButton("Complete") { _, _ -> actOnAction(dialog) }
        builder.setNegativeButton("Cancel") { _, _ -> }
        builder.show()


    }

    private fun actOnAction(dialog: DIALOGS?) {

        when (dialog) {

            DIALOGS.SEARCHPERSON -> {
                searchPerson(edtIdNumber?.text.toString())
            }

            DIALOGS.VERIFYPERSON -> {
                verifyPerson(
                    VerifyPersonModel(
                        id_number = edtIdNumber?.text.toString(),
                        first_name = edtFristName?.text.toString(),
                        surname = edtSirName?.text.toString(),
                        other_name = edtOtherName?.text.toString(),
                        phone_number = edtPhoneNumber?.text.toString(),
                        gender = edtGender?.text.toString(),
                        citizenship = edtCitizenship?.text.toString(),
                        date_of_birth = edtDateOfBirth?.text.toString()
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
                        registration_no = edtNcaContractorReg?.text.toString(),
                        contractor_name = edtNcaContractorName?.text.toString(),
                        town = edtNcaContractorTown?.text.toString(),
                        category = edtNcaContractorCategory?.text.toString(),
                        contractor_class = edtNcaContractorClass?.text.toString()
                    )
                )
            }
        }
    }

    @SuppressLint("InflateParams")
    private fun showVerificationResponseParams(paramsResponse: List<ParamsResponse>) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Verification")

        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.recycler_view, null)
        builder.setView(dialogView)
        builder.setCancelable(false)
        val adapter = VerificationParamsAdapter(paramsResponse)
        dialogView.recycler_view.adapter = adapter
        dialogView.recycler_view.layoutManager = LinearLayoutManager(this)
        builder.setNegativeButton("Cancel") { _, _ -> }
        builder.show()
    }

    private fun showPersonResponseDialog(person: Person) {
        val builder = AlertDialog.Builder(this)
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
        builder.setNegativeButton("Cancel") { _, _ -> }
        builder.show()

    }

    private fun showNcaContractorResponseDialog(ncaContractor: NcaContractor) {
        val builder = AlertDialog.Builder(this)
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
        builder.setNegativeButton("Cancel") { _, _ -> }
        builder.show()

    }

    private fun showNcaContractorsResponseDialog(ncaContractors: List<NcaContractor>) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Nca Contractors")
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.recycler_view, null)
        builder.setView(dialogView)
        builder.setCancelable(false)
        val adapter = NcaParamsAdapter(ncaContractors)
        dialogView.recycler_view.adapter = adapter
        dialogView.recycler_view.layoutManager = LinearLayoutManager(this)
        builder.setNegativeButton("Cancel") { _, _ -> }
        builder.show()

    }

    private fun showError(verifyException: VerifyException) {

        var errorrMessage = ""

        var errorArray: String? = verifyException.errors?.joinToString { "\'${it}\'" }
        if (errorArray == null || errorArray == "null") {
            errorArray = ""
        }
        if (verifyException.errorMessage != null) {
            errorrMessage = verifyException.errorMessage!!
        }
        Toast.makeText(this, errorrMessage + "\n" + errorArray, Toast.LENGTH_LONG).show()

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
                showError(verifyException)

            }
        })
    }


    private fun verifyPerson(verifyPersonModel: VerifyPersonModel) {
        v?.verifyPerson(verifyPersonModel, object : VerifyUserDetailsListener {
            override fun onCallStarted() {
                setProgressBarVisibility(View.VISIBLE)

            }

            override fun onResponse(paramsResponse: List<ParamsResponse>) {
                setProgressBarVisibility(View.GONE)
                showVerificationResponseParams(paramsResponse)

            }

            override fun onFailure(verifyException: VerifyException) {
                setProgressBarVisibility(View.GONE)
                showError(verifyException)
            }
        })
    }

    private fun searchNcaContractorById(contractorRegId: String) {
        v?.searchNcaContractorById(contractorRegId, object : SearchNcaContractorByIdListener {
            override fun onCallStarted() {
                setProgressBarVisibility(View.VISIBLE)

            }

            override fun onResponse(
                ncaContractor: NcaContractor
            ) {
                setProgressBarVisibility(View.GONE)
                showNcaContractorResponseDialog(ncaContractor)

            }

            override fun onFailure(verifyException: VerifyException) {
                setProgressBarVisibility(View.GONE)

                showError(verifyException)

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

                showError(verifyException)

            }
        })
    }

    private fun verifyNcaContractor(verifyNcaContractor: VerifyNcaContractor) {
        v?.verifyNcaContractor(verifyNcaContractor, object : VerifyNcaContractorListener {
            override fun onCallStarted() {
                setProgressBarVisibility(View.VISIBLE)

            }

            override fun onResponse(paramsResponse: List<ParamsResponse>) {
                setProgressBarVisibility(View.GONE)
                showVerificationResponseParams(paramsResponse)


            }

            override fun onFailure(verifyException: VerifyException) {
                setProgressBarVisibility(View.GONE)
                showError(verifyException)

            }
        })
    }

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


    enum class DIALOGS {
        SEARCHPERSON,
        VERIFYPERSON,
        SEARCHNCACONTRACTORID,
        SEARCHNCACONTRACTORNAME,
        VERIFYNCACONTRACTOR
    }

}
