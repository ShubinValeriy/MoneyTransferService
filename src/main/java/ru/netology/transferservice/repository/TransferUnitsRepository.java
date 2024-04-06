package ru.netology.transferservice.repository;

import org.springframework.stereotype.Repository;
import ru.netology.transferservice.model.TransferUnit;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class TransferUnitsRepository {
    private final Map<String,TransferStatus> statusMap;
    private final Map<String, TransferUnit>  transferUnitsMap;
    private final AtomicInteger transferUnitIDMaker;

    public TransferUnitsRepository() {
        this.statusMap = new ConcurrentHashMap<>();
        this.transferUnitsMap = new ConcurrentHashMap<>();
        transferUnitIDMaker = new AtomicInteger(0);
    }

    public String addTransferUnit(TransferUnit transferUnit){
        String transferUnitID = String.valueOf(transferUnitIDMaker.incrementAndGet());
        statusMap.put(transferUnitID,TransferStatus.LOAD);
        transferUnitsMap.put(transferUnitID, transferUnit);
        return transferUnitID;
    }
    public TransferStatus getTransferStatus (String transferUnitID){
        if (!statusMap.containsKey(transferUnitID)){
            return null;
        }
        return statusMap.get(transferUnitID);
    }

    public TransferUnit setConfirmTransferStatus(String transferUnitID){
        if (!statusMap.containsKey(transferUnitID)){
            return null;
        }
        statusMap.put(transferUnitID,TransferStatus.CONFIRM);
        return transferUnitsMap.get(transferUnitID);
    }

    public TransferUnit setErrorTransferStatus(String transferUnitID){
        if (!statusMap.containsKey(transferUnitID)){
            return null;
        }
        statusMap.put(transferUnitID,TransferStatus.ERROR);
        return transferUnitsMap.get(transferUnitID);
    }

    public boolean isEmpty(){
        return transferUnitsMap.isEmpty();
    }


}
