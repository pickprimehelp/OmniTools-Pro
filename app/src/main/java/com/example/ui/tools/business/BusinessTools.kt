package com.example.ui.tools.business

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color as AndroidColor
import android.graphics.Paint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ads.TopBannerAd
import com.example.util.ImageExportUtils
import java.text.NumberFormat
import java.util.Locale
import kotlin.math.pow

// ==========================================
// 1. INVOICE / BILL MAKER (With GST & Add Products)
// ==========================================

data class InvoiceItem(
    var name: String,
    var qty: Double,
    var rate: Double,
    var gstRate: Double = 18.0
) {
    val totalTaxable: Double get() = qty * rate
    val taxAmount: Double get() = totalTaxable * (gstRate / 100.0)
    val totalAmount: Double get() = totalTaxable + taxAmount
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InvoiceBillMakerScreen(onBack: () -> Unit) {
    BackHandler { onBack() }
    val context = LocalContext.current

    var businessName by remember { mutableStateOf("TechCraft Solutions") }
    var businessGst by remember { mutableStateOf("07AAAAA0000A1Z5") }
    var businessAddress by remember { mutableStateOf("Connaught Place, New Delhi - 110001") }
    var customerName by remember { mutableStateOf("Vikas Singhal") }
    var customerPhone by remember { mutableStateOf("+91 98112 34567") }
    var invoiceNumber by remember { mutableStateOf("INV-2026-089") }
    var invoiceDate by remember { mutableStateOf("29-09-2026") }

    val items = remember {
        mutableStateListOf(
            InvoiceItem("Website Design & Development", 1.0, 25000.0, 18.0),
            InvoiceItem("Cloud Hosting (1 Year)", 1.0, 4500.0, 18.0)
        )
    }

    // Calculations
    val subtotal = items.sumOf { it.totalTaxable }
    val totalGst = items.sumOf { it.taxAmount }
    val cgst = totalGst / 2.0
    val sgst = totalGst / 2.0
    val grandTotal = subtotal + totalGst

    val currencyFormat = NumberFormat.getCurrencyInstance(Locale("en", "IN"))

    Scaffold(
        topBar = {
            Column {
                TopAppBar(
                    title = { Text("Invoice & Bill Maker") },
                    navigationIcon = {
                        IconButton(onClick = onBack) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.surface)
                )
                TopBannerAd()
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Live Invoice Paper Preview
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(6.dp, RoundedCornerShape(12.dp)),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = BorderStroke(1.dp, Color(0xFFE2E8F0))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    // Header
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(businessName, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color(0xFF0F172A))
                            Text("GSTIN: $businessGst", fontSize = 11.sp, color = Color(0xFF475569))
                            Text(businessAddress, fontSize = 10.sp, color = Color(0xFF64748B))
                        }
                        Column(horizontalAlignment = Alignment.End) {
                            Surface(
                                color = Color(0xFF0F172A),
                                shape = RoundedCornerShape(4.dp)
                            ) {
                                Text("TAX INVOICE", color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
                            }
                            Spacer(Modifier.height(4.dp))
                            Text(invoiceNumber, fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF0F172A))
                            Text("Date: $invoiceDate", fontSize = 10.sp, color = Color(0xFF64748B))
                        }
                    }

                    HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp), color = Color(0xFFE2E8F0))

                    // Bill To
                    Text("BILL TO:", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color(0xFF64748B))
                    Text(customerName, fontWeight = FontWeight.SemiBold, fontSize = 13.sp, color = Color(0xFF0F172A))
                    Text("Phone: $customerPhone", fontSize = 11.sp, color = Color(0xFF475569))

                    Spacer(Modifier.height(12.dp))

                    // Items Table
                    Surface(
                        color = Color(0xFFF8FAFC),
                        shape = RoundedCornerShape(6.dp)
                    ) {
                        Column(modifier = Modifier.padding(8.dp)) {
                            Row(modifier = Modifier.fillMaxWidth()) {
                                Text("Item / Description", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFF334155), modifier = Modifier.weight(2f))
                                Text("Qty", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFF334155), modifier = Modifier.weight(0.7f), textAlign = TextAlign.Center)
                                Text("Rate", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFF334155), modifier = Modifier.weight(1.2f), textAlign = TextAlign.End)
                                Text("Total", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFF334155), modifier = Modifier.weight(1.2f), textAlign = TextAlign.End)
                            }
                            HorizontalDivider(modifier = Modifier.padding(vertical = 6.dp), color = Color(0xFFE2E8F0))

                            items.forEach { item ->
                                Row(modifier = Modifier.fillMaxWidth().padding(vertical = 3.dp)) {
                                    Text(item.name, fontSize = 11.sp, color = Color(0xFF1E293B), modifier = Modifier.weight(2f))
                                    Text("${item.qty.toInt()}", fontSize = 11.sp, color = Color(0xFF1E293B), modifier = Modifier.weight(0.7f), textAlign = TextAlign.Center)
                                    Text("₹${item.rate.toInt()}", fontSize = 11.sp, color = Color(0xFF1E293B), modifier = Modifier.weight(1.2f), textAlign = TextAlign.End)
                                    Text("₹${item.totalAmount.toInt()}", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF1E293B), modifier = Modifier.weight(1.2f), textAlign = TextAlign.End)
                                }
                            }
                        }
                    }

                    Spacer(Modifier.height(12.dp))

                    // Totals
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.End
                    ) {
                        Text("Subtotal: ₹${subtotal.toInt()}", fontSize = 12.sp, color = Color(0xFF475569))
                        Text("CGST: ₹${cgst.toInt()}", fontSize = 11.sp, color = Color(0xFF64748B))
                        Text("SGST: ₹${sgst.toInt()}", fontSize = 11.sp, color = Color(0xFF64748B))
                        HorizontalDivider(modifier = Modifier.width(180.dp).padding(vertical = 4.dp), color = Color(0xFFE2E8F0))
                        Text("Grand Total: ₹${grandTotal.toInt()}", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = Color(0xFF059669))
                    }
                }
            }

            // Export Actions
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Button(
                    onClick = {
                        val textBill = buildString {
                            appendLine("===== $businessName =====")
                            appendLine("GSTIN: $businessGst")
                            appendLine("Invoice No: $invoiceNumber  | Date: $invoiceDate")
                            appendLine("Bill To: $customerName ($customerPhone)")
                            appendLine("----------------------------------------")
                            items.forEach { item ->
                                appendLine("${item.name} x ${item.qty.toInt()} = ₹${item.totalAmount.toInt()}")
                            }
                            appendLine("----------------------------------------")
                            appendLine("Subtotal: ₹${subtotal.toInt()}")
                            appendLine("CGST: ₹${cgst.toInt()} | SGST: ₹${sgst.toInt()}")
                            appendLine("GRAND TOTAL: ₹${grandTotal.toInt()}")
                            appendLine("Thank you for your business!")
                        }
                        ImageExportUtils.copyToClipboard(context, textBill, "Invoice")
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(Icons.Filled.Receipt, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(Modifier.width(6.dp))
                    Text("Copy Bill Text")
                }
                OutlinedButton(
                    onClick = {
                        val bmp = renderInvoiceBitmap(businessName, businessGst, invoiceNumber, invoiceDate, customerName, customerPhone, items, grandTotal)
                        ImageExportUtils.shareBitmap(context, bmp, "Invoice $invoiceNumber")
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(Icons.Filled.Share, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(Modifier.width(6.dp))
                    Text("Share Invoice")
                }
            }

            // Business & Customer Details
            Text("Business Details", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            OutlinedTextField(value = businessName, onValueChange = { businessName = it }, label = { Text("Your Business Name") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = businessGst, onValueChange = { businessGst = it }, label = { Text("GST Number (GSTIN)") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = businessAddress, onValueChange = { businessAddress = it }, label = { Text("Business Address") }, modifier = Modifier.fillMaxWidth())

            Text("Client & Invoice Info", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            OutlinedTextField(value = customerName, onValueChange = { customerName = it }, label = { Text("Client / Customer Name") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = customerPhone, onValueChange = { customerPhone = it }, label = { Text("Client Phone Number") }, modifier = Modifier.fillMaxWidth())
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(value = invoiceNumber, onValueChange = { invoiceNumber = it }, label = { Text("Invoice #") }, modifier = Modifier.weight(1f))
                OutlinedTextField(value = invoiceDate, onValueChange = { invoiceDate = it }, label = { Text("Date") }, modifier = Modifier.weight(1f))
            }

            // Products / Items List
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Products & Services (${items.size})", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                Button(
                    onClick = { items.add(InvoiceItem("New Item", 1.0, 1000.0, 18.0)) },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Icon(Icons.Filled.Add, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(Modifier.width(4.dp))
                    Text("Add Product")
                }
            }

            items.forEachIndexed { index, item ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                ) {
                    Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("Item #${index + 1}", fontWeight = FontWeight.Bold, fontSize = 13.sp, modifier = Modifier.weight(1f))
                            if (items.size > 1) {
                                IconButton(onClick = { items.removeAt(index) }) {
                                    Icon(Icons.Filled.Delete, contentDescription = "Delete", tint = MaterialTheme.colorScheme.error)
                                }
                            }
                        }
                        OutlinedTextField(
                            value = item.name,
                            onValueChange = { item.name = it },
                            label = { Text("Product / Service Description") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            OutlinedTextField(
                                value = if (item.qty == 0.0) "" else item.qty.toString(),
                                onValueChange = { item.qty = it.toDoubleOrNull() ?: 1.0 },
                                label = { Text("Qty") },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                modifier = Modifier.weight(1f)
                            )
                            OutlinedTextField(
                                value = if (item.rate == 0.0) "" else item.rate.toString(),
                                onValueChange = { item.rate = it.toDoubleOrNull() ?: 0.0 },
                                label = { Text("Rate (₹)") },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                modifier = Modifier.weight(1.5f)
                            )
                            OutlinedTextField(
                                value = item.gstRate.toInt().toString(),
                                onValueChange = { item.gstRate = it.toDoubleOrNull() ?: 18.0 },
                                label = { Text("GST %") },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }
            }
        }
    }
}

private fun renderInvoiceBitmap(
    bizName: String,
    gstin: String,
    invNo: String,
    date: String,
    client: String,
    phone: String,
    items: List<InvoiceItem>,
    total: Double
): Bitmap {
    val width = 1080
    val height = 1500
    val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)

    canvas.drawColor(AndroidColor.WHITE)

    val paint = Paint().apply {
        isAntiAlias = true
        color = AndroidColor.BLACK
    }

    // Header
    paint.textSize = 50f
    paint.isFakeBoldText = true
    canvas.drawText(bizName, 60f, 120f, paint)

    paint.textSize = 28f
    paint.isFakeBoldText = false
    canvas.drawText("GSTIN: $gstin", 60f, 170f, paint)

    paint.textSize = 40f
    paint.isFakeBoldText = true
    paint.textAlign = Paint.Align.RIGHT
    canvas.drawText("TAX INVOICE", width - 60f, 120f, paint)

    paint.textSize = 28f
    paint.isFakeBoldText = false
    canvas.drawText("Invoice: $invNo", width - 60f, 170f, paint)
    canvas.drawText("Date: $date", width - 60f, 210f, paint)

    // Divider
    paint.strokeWidth = 3f
    paint.color = AndroidColor.LTGRAY
    canvas.drawLine(60f, 260f, width - 60f, 260f, paint)

    // Bill To
    paint.color = AndroidColor.BLACK
    paint.textAlign = Paint.Align.LEFT
    paint.textSize = 34f
    paint.isFakeBoldText = true
    canvas.drawText("Bill To: $client", 60f, 320f, paint)
    paint.textSize = 28f
    paint.isFakeBoldText = false
    canvas.drawText("Phone: $phone", 60f, 360f, paint)

    // Items table header
    paint.color = AndroidColor.parseColor("#F1F5F9")
    canvas.drawRect(60f, 420f, width - 60f, 480f, paint)

    paint.color = AndroidColor.BLACK
    paint.textSize = 28f
    paint.isFakeBoldText = true
    canvas.drawText("Item / Description", 80f, 460f, paint)
    canvas.drawText("Qty", 650f, 460f, paint)
    canvas.drawText("Rate", 780f, 460f, paint)
    canvas.drawText("Total", 920f, 460f, paint)

    paint.isFakeBoldText = false
    var currentY = 540f
    items.forEach { item ->
        canvas.drawText(item.name, 80f, currentY, paint)
        canvas.drawText("${item.qty.toInt()}", 650f, currentY, paint)
        canvas.drawText("₹${item.rate.toInt()}", 780f, currentY, paint)
        canvas.drawText("₹${item.totalAmount.toInt()}", 920f, currentY, paint)
        currentY += 60f
    }

    // Grand Total box
    paint.color = AndroidColor.parseColor("#059669")
    canvas.drawRect(width - 450f, currentY + 40f, width - 60f, currentY + 120f, paint)
    paint.color = AndroidColor.WHITE
    paint.textSize = 36f
    paint.isFakeBoldText = true
    canvas.drawText("GRAND TOTAL: ₹${total.toInt()}", width - 420f, currentY + 92f, paint)

    return bitmap
}

// ==========================================
// 2. GST CALCULATOR SCREEN
// ==========================================

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GstCalculatorScreen(onBack: () -> Unit) {
    BackHandler { onBack() }

    var amountText by remember { mutableStateOf("10000") }
    var gstRate by remember { mutableDoubleStateOf(18.0) }
    var isExclusive by remember { mutableStateOf(true) } // true: Add GST, false: Remove GST

    val baseAmount = amountText.toDoubleOrNull() ?: 0.0

    val (netAmount, gstAmount, totalAmount) = remember(baseAmount, gstRate, isExclusive) {
        if (isExclusive) {
            val gst = baseAmount * (gstRate / 100.0)
            Triple(baseAmount, gst, baseAmount + gst)
        } else {
            val net = baseAmount / (1.0 + (gstRate / 100.0))
            val gst = baseAmount - net
            Triple(net, gst, baseAmount)
        }
    }
    val cgst = gstAmount / 2.0
    val sgst = gstAmount / 2.0

    Scaffold(
        topBar = {
            Column {
                TopAppBar(
                    title = { Text("GST Calculator") },
                    navigationIcon = {
                        IconButton(onClick = onBack) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.surface)
                )
                TopBannerAd()
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Mode Selector: Add GST vs Remove GST
            TabRow(selectedTabIndex = if (isExclusive) 0 else 1) {
                Tab(selected = isExclusive, onClick = { isExclusive = true }, text = { Text("Add GST (Exclusive)") })
                Tab(selected = !isExclusive, onClick = { isExclusive = false }, text = { Text("Remove GST (Inclusive)") })
            }

            // Amount Input
            OutlinedTextField(
                value = amountText,
                onValueChange = { amountText = it },
                label = { Text(if (isExclusive) "Net Amount (₹)" else "Total Gross Amount (₹)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            // Preset GST Rates
            Text("Select GST Rate", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            val rates = listOf(3.0, 5.0, 12.0, 18.0, 28.0)
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                rates.forEach { rate ->
                    FilterChip(
                        selected = gstRate == rate,
                        onClick = { gstRate = rate },
                        label = { Text("${rate.toInt()}%") },
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            // Detailed Result Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text("GST Calculation Summary", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    HorizontalDivider()

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Base / Net Amount:", color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Text("₹${String.format("%.2f", netAmount)}", fontWeight = FontWeight.SemiBold)
                    }
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("CGST (${gstRate / 2}%):", color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Text("₹${String.format("%.2f", cgst)}", fontWeight = FontWeight.SemiBold)
                    }
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("SGST (${gstRate / 2}%):", color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Text("₹${String.format("%.2f", sgst)}", fontWeight = FontWeight.SemiBold)
                    }
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Total Tax Amount:", color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Text("₹${String.format("%.2f", gstAmount)}", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                    }

                    HorizontalDivider()
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Final Total Amount:", fontWeight = FontWeight.Bold, fontSize = 17.sp)
                        Text("₹${String.format("%.2f", totalAmount)}", fontWeight = FontWeight.Bold, fontSize = 19.sp, color = Color(0xFF059669))
                    }
                }
            }
        }
    }
}

// ==========================================
// 3. EMI CALCULATOR SCREEN
// ==========================================

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmiCalculatorScreen(onBack: () -> Unit) {
    BackHandler { onBack() }

    var loanAmountText by remember { mutableStateOf("500000") }
    var interestRateText by remember { mutableStateOf("9.5") }
    var tenureYearsText by remember { mutableStateOf("5") }

    val principal = loanAmountText.toDoubleOrNull() ?: 0.0
    val annualRate = interestRateText.toDoubleOrNull() ?: 0.0
    val tenureYears = tenureYearsText.toDoubleOrNull() ?: 0.0

    val monthlyRate = (annualRate / 12.0) / 100.0
    val totalMonths = tenureYears * 12.0

    val emi = remember(principal, monthlyRate, totalMonths) {
        if (principal > 0 && monthlyRate > 0 && totalMonths > 0) {
            val factor = (1.0 + monthlyRate).pow(totalMonths)
            (principal * monthlyRate * factor) / (factor - 1.0)
        } else 0.0
    }

    val totalPayment = emi * totalMonths
    val totalInterest = if (totalPayment > principal) totalPayment - principal else 0.0

    Scaffold(
        topBar = {
            Column {
                TopAppBar(
                    title = { Text("EMI Calculator") },
                    navigationIcon = {
                        IconButton(onClick = onBack) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.surface)
                )
                TopBannerAd()
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Big EMI Highlight
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Monthly EMI Payable", style = MaterialTheme.typography.titleSmall)
                    Spacer(Modifier.height(6.dp))
                    Text(
                        "₹${String.format("%,.0f", emi)}",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }

            OutlinedTextField(
                value = loanAmountText,
                onValueChange = { loanAmountText = it },
                label = { Text("Loan Amount (₹)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = interestRateText,
                onValueChange = { interestRateText = it },
                label = { Text("Annual Interest Rate (%)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = tenureYearsText,
                onValueChange = { tenureYearsText = it },
                label = { Text("Tenure (Years)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            // Breakdown Summary Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text("Loan Repayment Breakdown", fontWeight = FontWeight.Bold)
                    HorizontalDivider()
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Principal Loan Amount:")
                        Text("₹${String.format("%,.0f", principal)}", fontWeight = FontWeight.SemiBold)
                    }
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Total Interest Amount:")
                        Text("₹${String.format("%,.0f", totalInterest)}", fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.error)
                    }
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Total Amount Payable:")
                        Text("₹${String.format("%,.0f", totalPayment)}", fontWeight = FontWeight.Bold, color = Color(0xFF059669))
                    }
                }
            }
        }
    }
}

// ==========================================
// 4. PROFIT MARGIN & DISCOUNT CALCULATOR
// ==========================================

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfitDiscountCalculatorScreen(onBack: () -> Unit) {
    BackHandler { onBack() }

    var costPriceText by remember { mutableStateOf("1200") }
    var sellingPriceText by remember { mutableStateOf("1800") }

    val cost = costPriceText.toDoubleOrNull() ?: 0.0
    val selling = sellingPriceText.toDoubleOrNull() ?: 0.0

    val profit = selling - cost
    val marginPercent = if (selling > 0) (profit / selling) * 100.0 else 0.0
    val markupPercent = if (cost > 0) (profit / cost) * 100.0 else 0.0

    Scaffold(
        topBar = {
            Column {
                TopAppBar(
                    title = { Text("Profit Margin & Markup") },
                    navigationIcon = {
                        IconButton(onClick = onBack) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.surface)
                )
                TopBannerAd()
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = costPriceText,
                onValueChange = { costPriceText = it },
                label = { Text("Cost Price (₹)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = sellingPriceText,
                onValueChange = { sellingPriceText = it },
                label = { Text("Selling Price (₹)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text("Calculation Results", fontWeight = FontWeight.Bold)
                    HorizontalDivider()
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Gross Profit:")
                        Text(
                            "₹${String.format("%.2f", profit)}",
                            fontWeight = FontWeight.Bold,
                            color = if (profit >= 0) Color(0xFF059669) else MaterialTheme.colorScheme.error
                        )
                    }
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Profit Margin %:")
                        Text("${String.format("%.1f", marginPercent)}%", fontWeight = FontWeight.Bold)
                    }
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Markup %:")
                        Text("${String.format("%.1f", markupPercent)}%", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}
