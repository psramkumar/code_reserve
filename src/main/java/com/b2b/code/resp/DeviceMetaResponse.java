package com.b2b.code.resp;

public record DeviceMetaResponse(
        String deviceName,
        String technology,
        String _2g_bands,
        String _3g_bands,
        String _4g_bands) { }
