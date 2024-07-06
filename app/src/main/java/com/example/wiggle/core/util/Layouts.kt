package com.example.wiggle.core.util


import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmailTextField(
    modifier: Modifier =Modifier,
    emailState:MutableState<String>,
    color: Color,

    labelId:String,
    enabled:Boolean=true,
    imeAction: ImeAction= ImeAction.Next,
    onAction: KeyboardActions=KeyboardActions.Default
) {

    InputField(
        valueState = emailState,
        labelId = labelId,
        enabled = enabled,
        modifier = modifier,
        keyboardType = KeyboardType.Email,
        imeAction = imeAction,
        onAction = onAction,
        color=color
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputField(
    modifier: Modifier=Modifier,
    valueState: MutableState<String>,
    labelId:String,
    enabled: Boolean,
    isSingleLine:Boolean=true,
    keyboardType: KeyboardType=KeyboardType.Text,
    imeAction: ImeAction=ImeAction.Next,
    color: Color,
    onAction: KeyboardActions=KeyboardActions.Default
)
{

    OutlinedTextField(value = valueState.value, onValueChange = {
        valueState.value=it
    },modifier=modifier,enabled=enabled,
        label= {Text(text = labelId,style=MaterialTheme.typography.labelMedium,
            color= color)},
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = Color.Black,
            cursorColor = Color.Black,
            unfocusedContainerColor = Color.White,
            focusedContainerColor = MaterialTheme.colorScheme.secondary,
          //  containerColor = Color(0xF706666),
            //placeholderColor = Color.Black
        ), singleLine = isSingleLine, keyboardOptions = KeyboardOptions(keyboardType=KeyboardType.Email, imeAction = imeAction)
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordInput(
    modifier: Modifier,
    passwordState: MutableState<String>,
    labelId: String,
    enabled: Boolean,
    passwordVisiblity: MutableState<Boolean>,
    imeAction: ImeAction=ImeAction.Done,
    onAction: KeyboardActions=KeyboardActions.Default,
    color: Color
) {
    val visualTransformation= if(passwordVisiblity.value) VisualTransformation.None else
        PasswordVisualTransformation()
    OutlinedTextField(value = passwordState.value, onValueChange = {
        passwordState.value=it
    },modifier=modifier,
        enabled=enabled,
        label= {Text(text = labelId,style=MaterialTheme.typography.labelMedium,
            color= color)},
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = Color.Black,
            cursorColor = Color.Black,
            unfocusedContainerColor = Color.White,
            focusedContainerColor = MaterialTheme.colorScheme.secondary,
           // containerColor = Color(0xF706666),
            //placeholderColor = Color.Black
        ), singleLine =true,
        keyboardOptions = KeyboardOptions(keyboardType= KeyboardType.Password, imeAction = imeAction),
        visualTransformation = visualTransformation,
        trailingIcon = {

            val iconImage= if(passwordVisiblity.value)
            {
                Icons.Filled.Visibility
            }else{
                Icons.Filled.VisibilityOff
            }
            IconButton(onClick = { passwordVisiblity.value=!passwordVisiblity.value})
            {
                Icon(iconImage,null)
            }
        },
        keyboardActions = onAction
    )

}


@Composable
fun ButtonComponent(
    isCreateAccount:Boolean,
    loading:Boolean=false,
    validInputs:Boolean,
    onClick:()->Unit)
{
    Button(onClick = onClick, modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 25.dp)
        .height(40.dp), colors = ButtonDefaults.buttonColors(
        containerColor = MaterialTheme.colorScheme.secondary,
        disabledContainerColor = Color(0xFF79B9D6),
        contentColor = Color.White), shape= RoundedCornerShape(4.dp),
        enabled = !loading && validInputs
    )
    {
        Text(text = if(isCreateAccount) "Sign up" else "Login"
            ,style= MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Medium))
    }
}


