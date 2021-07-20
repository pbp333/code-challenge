package com.codechallenge.commitviewer.application.port;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codechallenge.commitviewer.application.CommitRetrieverFactory;

@Service
public class CommitRetrieverFactoryImpl implements CommitRetrieverFactory {

    private final Map<CommitRetriverStrategy, CommitRetrieverPort> portsByStrategy =
            new EnumMap<>(CommitRetriverStrategy.class);

    @Autowired
    public CommitRetrieverFactoryImpl(List<CommitRetrieverPort> ports) {
        ports.forEach(port -> portsByStrategy.put(port.getStrategy(), port));
    }


    @Override
    public Optional<CommitRetrieverPort> getPort(CommitRetriverStrategy strategy) {
        return Optional.ofNullable(portsByStrategy.get(strategy));
    }

}
