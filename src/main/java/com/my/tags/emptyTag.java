package com.my.tags;

import com.my.db.entity.Card;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class emptyTag extends TagSupport {
    @Override
    public int doStartTag() throws JspException {
        List<Card> cards = new ArrayList<>();

        Optional<Card> first = cards.stream().filter(x -> x.getIdcard() == 1).findFirst();
        first.orElse(null);

        JspWriter out = pageContext.getOut();

        try {
            out.print(LocalDateTime.now());
        } catch (IOException e) {
            throw new JspException(e);
        }

        return SKIP_BODY;
    }
}
