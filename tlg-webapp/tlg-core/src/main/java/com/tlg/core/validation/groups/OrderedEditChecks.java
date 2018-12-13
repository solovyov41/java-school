package com.tlg.core.validation.groups;

import javax.validation.GroupSequence;

@GroupSequence({EditChecks.class, DatabaseChecks.class})
public interface OrderedEditChecks {
}
