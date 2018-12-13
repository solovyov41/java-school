package com.tlg.core.validation.groups;

import javax.validation.GroupSequence;

@GroupSequence({RegistrationChecks.class, DatabaseChecks.class})
public interface OrderedRegistrationChecks {
}
