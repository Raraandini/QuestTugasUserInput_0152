package com.example.prak4

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BiodataScreen(modifier: Modifier = Modifier) {
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    var textNamaDepan by remember { mutableStateOf("") }
    var textNamaBelakang by remember { mutableStateOf("") }
    var textEmail by remember { mutableStateOf("") }
    var textExperience by remember { mutableStateOf("") }
    var textTglLahir by remember { mutableStateOf("") }
    var showDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()
    var expandedExperience by remember { mutableStateOf(false) }
    val experienceOptions = listOf("< 1 Tahun", "1 - 2 Tahun", "3 - 5 Tahun", "> 5 Tahun")
    var checkAndroid by remember { mutableStateOf(false) }
    var checkUiUx by remember { mutableStateOf(false) }
    var checkWeb by remember { mutableStateOf(false) }

    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        datePickerState.selectedDateMillis?.let { millis ->
                            val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                            sdf.timeZone = TimeZone.getTimeZone("UTC")
                            textTglLahir = sdf.format(Date(millis))
                        }
                        showDatePicker = false
                    },
                ) { Text("OK") }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) { Text("Batal") }
            },
        ) {
            DatePicker(state = datePickerState)
        }
    }
    Box(
        modifier = modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    focusManager.clearFocus()
                    keyboardController?.hide()
                })
            },
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.38f),
            contentAlignment = Alignment.Center,
        ) {
            Image(
                painter = painterResource(id = R.drawable.bg_image),
                contentDescription = stringResource(id = R.string.desc_bg),
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
            )
            Text(
                text = stringResource(id = R.string.header_title),
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = colorResource(id = R.color.header_text_color),
                textAlign = TextAlign.Center,
                lineHeight = 34.sp,
                modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_large)),
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.70f)
                .align(Alignment.BottomCenter)
                .clip(RoundedCornerShape(
                    topStart = dimensionResource(id = R.dimen.corner_radius_box),
                    topEnd = dimensionResource(id = R.dimen.corner_radius_box),
                ))
                .background(colorResource(id = R.color.white_background))
                .padding(dimensionResource(id = R.dimen.padding_large))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = stringResource(id = R.string.form_subtitle),
                    fontSize = dimensionResource(id = R.dimen.text_subtitle).value.sp,
                    color = colorResource(id = R.color.gray_subtitle),
                    textAlign = TextAlign.Center,
                )
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_large)))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium)),
                ) {
                    OutlinedTextField(
                        value = textNamaDepan,
                        onValueChange = { textNamaDepan = it },
                        label = { Text(stringResource(id = R.string.hint_nama_depan)) },
                        modifier = Modifier.weight(1f),
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    )
                    OutlinedTextField(
                        value = textNamaBelakang,
                        onValueChange = { textNamaBelakang = it },
                        label = { Text(stringResource(id = R.string.hint_nama_belakang)) },
                        modifier = Modifier.weight(1f),
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    )
                }
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_medium)))

                OutlinedTextField(
                    value = textEmail,
                    onValueChange = { textEmail = it },
                    label = { Text(stringResource(id = R.string.hint_email)) },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Done,
                    ),
                )
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_medium)))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium)),
                ) {
                    Box(modifier = Modifier.weight(1f)) {
                        OutlinedTextField(
                            value = textTglLahir,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text(stringResource(id = R.string.hint_tgl_lahir)) },
                            modifier = Modifier.fillMaxWidth(),
                            trailingIcon = {
                                IconButton(onClick = { showDatePicker = true }) {
                                    Icon(Icons.Default.DateRange, contentDescription = null)
                                }
                            },
                        )
                        Box(modifier = Modifier.matchParentSize().clickable { showDatePicker = true })
                    }

                }
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_large)))
            }
        }
    }
}