package com.overlaydemo.controls

class OverlayData(private var data: List<DataModel>) {

    fun getData(): List<DataModel> {
        return data
    }

    fun setData(data: List<DataModel>) {
        this.data = data
    }

    fun component1(): List<DataModel> {
        return data
    }

    fun copy(data: List<DataModel>): OverlayData {
        return OverlayData(data)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is OverlayData) return false
        return data == other.data
    }

    override fun hashCode(): Int {
        return data.hashCode()
    }

    override fun toString(): String {
        return "OverlayData(data=$data)"
    }
}
