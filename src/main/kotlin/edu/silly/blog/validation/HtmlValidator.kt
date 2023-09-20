package edu.silly.blog.validation

import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import org.slf4j.LoggerFactory
import org.xml.sax.ErrorHandler
import org.xml.sax.SAXParseException
import javax.xml.parsers.DocumentBuilderFactory

// This is a naive implementation of html validation.
// Has some overhead, but required not so often, so may even work.

class HtmlValidator : ConstraintValidator<CorrectHtml, String> {
    private val log = LoggerFactory.getLogger(HtmlValidator::class.java)

    override fun isValid(value: String?, context: ConstraintValidatorContext): Boolean {
        if (value == null)
            return true

        val docBuilderFactory = DocumentBuilderFactory.newInstance()!!
        docBuilderFactory.isValidating = true
        val docBuilder = docBuilderFactory.newDocumentBuilder()!!

        val errorHandler = object : ErrorHandler {
            override fun warning(exception: SAXParseException?) =
                log.warn("Warning in html validator: ${exception?.message ?: "error"}")

            override fun error(exception: SAXParseException?) =
                log.warn("Error in html validator: ${exception?.message ?: "error"}")

            override fun fatalError(exception: SAXParseException?) =
                log.error("Fatal error in html validator: ${exception?.message ?: "error"}")
        }

        docBuilder.setErrorHandler(errorHandler)

        return try {
            docBuilder.parse(value.byteInputStream())
            true
        } catch (_: Exception) {
            false
        }
    }
}