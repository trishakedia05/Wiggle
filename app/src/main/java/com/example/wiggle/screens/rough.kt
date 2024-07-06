package com.example.wiggle.screens

//import com.example.wiggle.screens.signup.SignupViewModel
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.rememberScrollState
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.foundation.text.KeyboardActions
//import androidx.compose.foundation.verticalScroll
//import androidx.compose.material3.Button
//import androidx.compose.material3.ButtonDefaults
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Surface
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.saveable.rememberSaveable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.ExperimentalComposeUiApi
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.focus.FocusRequester
//import androidx.compose.ui.focus.focusRequester
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.platform.LocalSoftwareKeyboardController
//import androidx.compose.ui.text.font.Font
//import androidx.compose.ui.text.font.FontFamily
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.googlefonts.Font
//import androidx.compose.ui.text.googlefonts.GoogleFont
//import androidx.compose.ui.text.style.TextAlign
//import androidx.compose.ui.text.style.TextDecoration
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.hilt.navigation.compose.hiltViewModel
//import androidx.navigation.NavController
//import com.example.bullseye.navigation.AllScreens
//import com.example.wiggle.utils.ButtonComponent
//import com.example.wiggle.utils.EmailTextField
//import com.example.wiggle.utils.PasswordInput
//import com.example.wiggle.utils.provider
//import androidx.compose.material3.Text as Text1
//
//
//@OptIn(ExperimentalComposeUiApi::class)
//@Composable
//fun WSignUpScreen(navController: NavController,viewModel: SignupViewModel = hiltViewModel())
//{
//    val showLoginForm= rememberSaveable { val mutableStateOf = mutableStateOf(true)
//        mutableStateOf
//    }
//    Surface(modifier= Modifier.fillMaxSize(),color= Color.White)
//    {
//        Column {
//            if (showLoginForm.value){
//                UserForm(loading = false, isCreateAccount = false)
//                { email, password ->
//                    {
//                        navController.navigate(AllScreens.MainScreen.name)
//                    }
//                } }
//            else{
//                UserForm(loading=false, isCreateAccount = true){
//                        email,password->
//                    {
//                        navController.navigate(AllScreens.MainScreen.name)
//                    }
//                }
//            }
//            Spacer(modifier = Modifier.height(160.dp))
//
//        }
//        Column (horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Bottom)
//        {
//            Spacer(modifier = Modifier.height(10.dp))
//            Row(){
//                if(!showLoginForm.value)
//                {
//                    Text1(text = "Already have an account? ",color= Color.Black, style = MaterialTheme.typography.labelLarge, textAlign = TextAlign.Center)
//                    Text1(text = "Login", textDecoration = TextDecoration.Underline,modifier=Modifier.clickable { showLoginForm.value=!showLoginForm.value },color= Color.Black, style = MaterialTheme.typography.labelLarge, textAlign = TextAlign.Center)
//                }else
//                {
//                    Text1(text = "Don't have an account? ",color= Color.Black, style = MaterialTheme.typography.labelLarge, textAlign = TextAlign.Center)
//                    Text1(text = "Sign up", textDecoration = TextDecoration.Underline,modifier=Modifier.clickable { showLoginForm.value=!showLoginForm.value },color= Color.Black, style = MaterialTheme.typography.labelLarge, textAlign = TextAlign.Center)
//                }
//
//            }
//            Spacer(modifier = Modifier.height(20.dp))
//            Text1(text = "Or continue with",color= Color.Black, style = MaterialTheme.typography.labelLarge, textAlign = TextAlign.Center)
//            Spacer(modifier = Modifier.height(20.dp))
//            GoogleSignIn(navController){}
//            Spacer(modifier = Modifier.height(235.dp))
//
//        }
//
//
//
//    }
//
//
//}
//
//
//
//@Composable
//private fun SignSection(isCreateAccount: Boolean)
//{
//    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.TopCenter)
//    {
//        Spacer(modifier = Modifier.height(30.dp))
//        Text1(
//            text = if(isCreateAccount) "Sign up" else "Login", style = MaterialTheme.typography.headlineLarge, color = Color.Black,
//            fontFamily = FontFamily(
//                Font(
//                    googleFont = GoogleFont("PT Serif"),
//                    fontProvider = provider
//                )
//            ), fontWeight = FontWeight.Bold, modifier = Modifier
//                .padding(bottom = 10.dp)
//                .align(
//                    Alignment.BottomCenter
//                )
//        )
//
//    }
//}
//
//@OptIn(ExperimentalComposeUiApi::class)
//@Composable
//fun UserForm(
//    loading:Boolean=false,
//    isCreateAccount:Boolean=false,
//    onDone:(String,String)->Unit
//)
//{
//    val email= rememberSaveable { mutableStateOf("") }
//    val password= rememberSaveable { mutableStateOf("") }
//    val passwordVisiblity= rememberSaveable { mutableStateOf(false) }
//    val passwordFocusRequest= FocusRequester.Default
//    val keyboardController=LocalSoftwareKeyboardController.current
//    val valid= remember(email.value,password.value){
//        email.value.trim().isNotEmpty() && password.value.trim().isNotEmpty()
//
//    }
//
//    Surface (color= Color.White){
//        Column(modifier= Modifier.fillMaxSize())
//        {
//            Spacer(modifier = Modifier.height(50.dp))
//            SignSection(isCreateAccount)
//            Spacer(modifier = Modifier.height(36.dp))
//            Column(modifier= Modifier
//                .fillMaxSize()
//                .padding(horizontal = 30.dp)
//                .verticalScroll(rememberScrollState()), horizontalAlignment = Alignment.CenterHorizontally)
//            {
//                EmailTextField(emailState = email, labelId = "Email",
//                    color=MaterialTheme.colorScheme.onSecondary, enabled = !loading, onAction = KeyboardActions{
//                        passwordFocusRequest.requestFocus()
//                    })
//                Spacer(modifier = Modifier.height(15.dp))
//                PasswordInput(
//                    modifier=Modifier.focusRequester(passwordFocusRequest),
//                    passwordState=password,
//                    labelId="Password",
//                    color = MaterialTheme.colorScheme.onSecondary,
//                    enabled=!loading,
//                    passwordVisiblity=passwordVisiblity,
//                    onAction=KeyboardActions{
//                        if(!valid) return@KeyboardActions
//                        onDone(email.value.trim(),password.value.trim())
//                    }
//                )
//                Spacer(modifier = Modifier.height(30.dp))
//                ButtonComponent(isCreateAccount=isCreateAccount, loading = loading, validInputs = valid){
//                    onDone(email.value.trim(),password.value.trim())
//                    keyboardController?.hide()
//                }
//                Spacer(modifier = Modifier.height(20.dp))
//
//
//            }
//        }
//    }
//
//}
//
//
//@Composable
//fun GoogleSignIn(
//    navController: NavController,
//    onSignInClick:()->Unit
//)
//{
//    Button(onClick = {  navController.navigate(AllScreens.MainScreen.name) }, modifier = Modifier
//        .fillMaxWidth()
//        .padding(horizontal = 50.dp)
//        .height(40.dp), colors = ButtonDefaults.buttonColors(
//        containerColor = Color(0xFF5FBAE4),
//        contentColor = Color.White),
//        shape= RoundedCornerShape(4.dp),
//        enabled = true
//    )
//    {
//        Text1(text = "Google", fontSize = 15.sp,
//
//            style= MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Medium))
//    }
//
//}
