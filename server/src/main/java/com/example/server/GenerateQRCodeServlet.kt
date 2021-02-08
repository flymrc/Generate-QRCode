package com.example.server

import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import org.apache.logging.log4j.LogManager
import java.awt.image.BufferedImage
import javax.imageio.ImageIO
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebServlet(name = "GenerateQRCodeServlet", urlPatterns = ["/qrcode"])
class GenerateQRCodeServlet : HttpServlet() {
    private val logger = LogManager.getLogger("GenerateQRCodeServlet")

    override fun doGet(req: HttpServletRequest, resp: HttpServletResponse) {
        val content = req.getParameter("content") ?: "FAKE!"

        resp.contentType = "image/png"
        val image = generateQRCode(content)
        ImageIO.write(image, "PNG", resp.outputStream)
    }

    private fun generateQRCode(text: String): BufferedImage {
        val width = 500
        val height = 500
        val image = BufferedImage(width, height, 1)
        val codeWriter = MultiFormatWriter()
        try {
            val bitMatrix = codeWriter.encode(text, BarcodeFormat.QR_CODE, width, height)
            for (x in 0 until width) {
                for (y in 0 until height) {
                    image.setRGB(x, y, if (bitMatrix[x, y]) -16777216 else -1)
                }
            }
        } catch (e: WriterException) {
            logger.debug("generateQRCode: ${e.message}")
        }
        return image
    }
}
