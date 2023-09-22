package com.cotta.diego

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.symbol.FunctionKind
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSAnnotation
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.google.devtools.ksp.symbol.KSPropertyDeclaration
import com.google.devtools.ksp.symbol.KSTypeArgument
import com.google.devtools.ksp.symbol.KSValueArgument
import com.google.devtools.ksp.symbol.KSVisitorVoid
import com.google.devtools.ksp.validate
import java.io.OutputStream

class RecyclerViewAdapterProcessor(
    private val codeGenerator: CodeGenerator,
    private val logger: KSPLogger,
    private val options: Map<String, String>
) : SymbolProcessor {

    override fun process(resolver: Resolver): List<KSAnnotated> {
        val symbols =
            resolver.getSymbolsWithAnnotation("com.cotta.diego.annotation.RecyclerViewAdapter")
                .filterIsInstance<KSFunctionDeclaration>()

        if (!symbols.iterator().hasNext()) return emptyList()

        val file: OutputStream = codeGenerator.createNewFile(
            dependencies = Dependencies(false, *resolver.getAllFiles().toList().toTypedArray()),
            packageName = "com.cotta.diego",
            fileName = "RecyclerViewAdapters"
        )
        file += "package com.cotta.diego\n"
        symbols.forEach { it.accept(Visitor(file), Unit) }

        file.close()
        return symbols.filterNot { it.validate() }.toList()
    }

    inner class Visitor(private val file: OutputStream) : KSVisitorVoid() {

        override fun visitFunctionDeclaration(function: KSFunctionDeclaration, data: Unit) {
            if (function.functionKind != FunctionKind.TOP_LEVEL) {
                logger.error("Only TOP_LEVEL can be annotated with @RecyclerViewAdapter", function)
                return
            }

            // Getting the @Function annotation object.
            val annotation: KSAnnotation = function.annotations.first {
                it.shortName.asString() == "RecyclerViewAdapter"
            }

            // Getting the 'name' argument object from the @Function.
            val nameArgument: KSValueArgument = annotation.arguments
                .first { arg -> arg.name?.asString() == "name" }

            // Getting the value of the 'name' argument.
            val functionName = nameArgument.value as String

            file += "\n"

        }


        override fun visitPropertyDeclaration(property: KSPropertyDeclaration, data: Unit) {
            super.visitPropertyDeclaration(property, data)
        }

        override fun visitTypeArgument(typeArgument: KSTypeArgument, data: Unit) {
            super.visitTypeArgument(typeArgument, data)
        }

    }

    operator fun OutputStream.plusAssign(str: String) {
        this.write(str.toByteArray())
    }
}