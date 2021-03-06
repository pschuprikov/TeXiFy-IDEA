package nl.rubensten.texifyidea.inspections.latex

import com.intellij.codeInspection.InspectionManager
import com.intellij.codeInspection.LocalQuickFix
import com.intellij.codeInspection.ProblemDescriptor
import com.intellij.codeInspection.ProblemHighlightType
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiDocumentManager
import com.intellij.psi.PsiFile
import nl.rubensten.texifyidea.file.LatexFileType
import nl.rubensten.texifyidea.insight.InsightGroup
import nl.rubensten.texifyidea.inspections.TexifyInspectionBase
import nl.rubensten.texifyidea.psi.LatexBeginCommand
import nl.rubensten.texifyidea.util.childrenOfType
import nl.rubensten.texifyidea.util.referencedFileSet

/**
 * @author Ruben Schellekens
 */
open class LatexMissingDocumentEnvironmentInspection : TexifyInspectionBase() {

    override fun getInspectionGroup() = InsightGroup.LATEX

    override fun getDisplayName() = "Missing document environment"

    override fun getInspectionId() = "MissingDocumentEnvironment"

    override fun inspectFile(file: PsiFile, manager: InspectionManager, isOntheFly: Boolean): List<ProblemDescriptor> {
        val descriptors = descriptorList()

        // Workaround, because .sty files also return LatexFileType.INSTANCE
        if (file.virtualFile.extension != LatexFileType.defaultExtension) {
            return descriptors
        }

        for (referencedFile in file.referencedFileSet()) {
            val beginCommands = referencedFile.childrenOfType(LatexBeginCommand::class)
            if (beginCommands.any { it.text == "\\begin{document}" }) {
                return descriptors
            }
        }

        descriptors.add(
                manager.createProblemDescriptor(
                        file,
                        "Document doesn't contain a document environment.",
                        InspectionFix(),
                        ProblemHighlightType.GENERIC_ERROR,
                        isOntheFly
                )
        )

        return descriptors
    }

    /**
     * @author Ruben Schellekens
     */
    private class InspectionFix : LocalQuickFix {

        override fun getFamilyName() = "Add a document environment"

        override fun applyFix(project: Project, descriptor: ProblemDescriptor) {
            val psiElement = descriptor.psiElement
            val file = psiElement.containingFile
            val document = PsiDocumentManager.getInstance(project).getDocument(file)

            document?.insertString(document.textLength, """
                |
                |\begin{document}
                |
                |
                |
                |\end{document}""".trimMargin())
        }
    }
}
