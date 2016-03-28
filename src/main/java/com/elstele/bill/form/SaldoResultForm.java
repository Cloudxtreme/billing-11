package com.elstele.bill.form;

import java.util.ArrayList;
import java.util.List;

public class SaldoResultForm {

    private Float totalStartKredit = 0f;
    private Float totalStartDebet = 0f;
    private Float totalDebet = 0f;
    private Float totalSumNachisl = 0f;
    private Float totalKreditPidv = 0f;
    private Float totalKreditYsb = 0f;
    private Float totalKredit24 = 0f;
    private Float totalKreditKassa = 0f;
    private Float totalSumOplaty = 0f;
    private Float totalFinishDebet = 0f;
    private Float totalFinishKredit = 0f;
    private Float totalAvansPrev = 0f;
    private Float totalAvans = 0f;
    private Float totalNds = 0f;

    private List<SaldoForm> saldoForms = new ArrayList<SaldoForm>();

    public void addSaldoForm(SaldoForm saldo){
        this.saldoForms.add(saldo);
    }

    public Float getTotalStartKredit() {
        return totalStartKredit;
    }

    public void setTotalStartKredit(Float totalStartKredit) {
        this.totalStartKredit = totalStartKredit;
    }

    public void addTotalStartKredit(Float totalStartKredit) {
        this.totalStartKredit = this.totalStartKredit + totalStartKredit;
    }


    public Float getTotalStartDebet() {
        return totalStartDebet;
    }

    public void setTotalStartDebet(Float totalStartDebet) {
        this.totalStartDebet = totalStartDebet;
    }

    public void addTotalStartDebet(Float totalStartDebet) {
        this.totalStartDebet = this.totalStartDebet + totalStartDebet;
    }

    public Float getTotalDebet() {
        return totalDebet;
    }

    public void setTotalDebet(Float totalDebet) {
        this.totalDebet = totalDebet;
    }

    public void addTotalDebet(Float totalDebet) {
        this.totalDebet = this.totalDebet + totalDebet;
    }

    public Float getTotalSumNachisl() {
        return totalSumNachisl;
    }

    public void setTotalSumNachisl(Float totalSumNachisl) {
        this.totalSumNachisl = totalSumNachisl;
    }

    public void addTotalSumNachisl(Float totalSumNachisl) {
        this.totalSumNachisl = this.totalSumNachisl + totalSumNachisl;
    }

    public Float getTotalKreditPidv() {
        return totalKreditPidv;
    }

    public void setTotalKreditPidv(Float totalKreditPidv) {
        this.totalKreditPidv = totalKreditPidv;
    }

    public void addTotalKreditPidv(Float totalKreditPidv) {
        this.totalKreditPidv = this.totalKreditPidv + totalKreditPidv;
    }

    public Float getTotalKreditYsb() {
        return totalKreditYsb;
    }

    public void setTotalKreditYsb(Float totalKreditYsb) {
        this.totalKreditYsb = totalKreditYsb;
    }

    public void addTotalKreditYsb(Float totalKreditYsb) {
        this.totalKreditYsb = this.totalKreditYsb + totalKreditYsb;
    }

    public Float getTotalKredit24() {
        return totalKredit24;
    }

    public void setTotalKredit24(Float totalKredit24) {
        this.totalKredit24 = totalKredit24;
    }

    public void addTotalKredit24(Float totalKredit24) {
        this.totalKredit24 = this.totalKredit24 + totalKredit24;
    }

    public Float getTotalKreditKassa() {
        return totalKreditKassa;
    }

    public void setTotalKreditKassa(Float totalKreditKassa) {
        this.totalKreditKassa = totalKreditKassa;
    }

    public void addTotalKreditKassa(Float totalKreditKassa) {
        this.totalKreditKassa = this.totalKreditKassa + totalKreditKassa;
    }

    public Float getTotalSumOplaty() {
        return totalSumOplaty;
    }

    public void setTotalSumOplaty(Float totalSumOplaty) {
        this.totalSumOplaty = totalSumOplaty;
    }

    public void addTotalSumOplaty(Float totalSumOplaty) {
        this.totalSumOplaty = this.totalSumOplaty + totalSumOplaty;
    }

    public Float getTotalFinishDebet() {
        return totalFinishDebet;
    }

    public void setTotalFinishDebet(Float totalFinishDebet) {
        this.totalFinishDebet = totalFinishDebet;
    }

    public void addTotalFinishDebet(Float totalFinishDebet) {
        this.totalFinishDebet = this.totalFinishDebet + totalFinishDebet;
    }

    public Float getTotalFinishKredit() {
        return totalFinishKredit;
    }

    public void setTotalFinishKredit(Float totalFinishKredit) {
        this.totalFinishKredit = totalFinishKredit;
    }

    public void addTotalFinishKredit(Float totalFinishKredit) {
        this.totalFinishKredit = this.totalFinishKredit + totalFinishKredit;
    }

    public Float getTotalAvansPrev() {
        return totalAvansPrev;
    }

    public void setTotalAvansPrev(Float totalAvansPrev) {
        this.totalAvansPrev = totalAvansPrev;
    }

    public void addTotalAvansPrev(Float totalAvansPrev) {
        this.totalAvansPrev = this.totalAvansPrev + totalAvansPrev;
    }

    public Float getTotalAvans() {
        return totalAvans;
    }

    public void setTotalAvans(Float totalAvans) {
        this.totalAvans = totalAvans;
    }

    public void addTotalAvans(Float totalAvans) {
        this.totalAvans = this.totalAvans + totalAvans;
    }

    public Float getTotalNds() {
        return totalNds;
    }

    public void setTotalNds(Float totalNds) {
        this.totalNds = totalNds;
    }

    public void addTotalNds(Float totalNds) {
        this.totalNds = this.totalNds + totalNds;
    }

    public List<SaldoForm> getSaldoForms() {
        return saldoForms;
    }

    public void setSaldoForms(List<SaldoForm> saldoForms) {
        this.saldoForms = saldoForms;
    }
}
