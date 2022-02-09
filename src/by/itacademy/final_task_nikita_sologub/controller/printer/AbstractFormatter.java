package by.itacademy.final_task_nikita_sologub.controller.printer;

import static by.itacademy.final_task_nikita_sologub.controller.printer.Constant.BLACK;
import static by.itacademy.final_task_nikita_sologub.controller.printer.Constant.FOOTER;
import static by.itacademy.final_task_nikita_sologub.controller.printer.Constant.RESET_COLOUR;

abstract class AbstractFormatter {
    protected final StringBuilder builder;
    private String mainColour;
    private String contrastColour;

    AbstractFormatter(StringBuilder builder) {
        this.builder = builder;
        mainColour = contrastColour = BLACK; // by default
    }

    void appendElementAndBorder(String elementName, String content, String border) {
        appendElementNameAndSetColors(elementName);
        builder.append(content);
        builder.append(border);
    }

    void appendElementAndBorder(String elementName, int content, String border) {
        appendElementNameAndSetColors(elementName);
        builder.append(content);
        builder.append(border);
    }

    private void appendElementNameAndSetColors(String elementName) {
        builder.append(mainColour);
        builder.append(elementName);
        builder.append(contrastColour);
    }

    void flushResources() {
        setMainAndContrastColours(BLACK, BLACK);// By default
        builder.setLength(0); // Flush resources
    }

    void setMainAndContrastColours(String mainColour, String contrastColour) {
        this.mainColour = mainColour;
        this.contrastColour = contrastColour;
    }

    void appendFooter() {
        builder.append(mainColour);
        builder.append(FOOTER);
        builder.append(RESET_COLOUR);
    }

    void appendEmptyBody(String emptyBody) {
        builder.append(emptyBody);
    }

    void appendHeader(String header) {
        builder.append(mainColour);
        builder.append(header);
    }
}