package org.zakariya.stickyheaders;

import android.support.annotation.Nullable;

public class AdapterListUpdateCallback implements SectionListUpdateCallback{

    private final SectioningAdapter adapter;

    public AdapterListUpdateCallback(SectioningAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    public void onItemInserted(int section, int item, int count) {
        adapter.notifySectionItemRangeInserted(section, item, count);
    }

    @Override
    public void onItemRemoved(int section, int item, int count) {
        adapter.notifySectionItemRangeRemoved(section, item, count);
    }

    @Override
    public void onItemChanged(int section, int item) {
        adapter.notifySectionItemChanged(section, item);
    }

    @Override
    public void onSectionInserted(int section) {
        adapter.notifySectionInserted(section);
    }

    @Override
    public void onSectionRemoved(int section, int item, int count) {
        adapter.notifySectionRemoved(section);
    }

    @Override
    public void onSectionFooterInserted(int section) {
        adapter.notifySectionFooterInserted(section);
    }

    @Override
    public void onSectionFooterRemoved(int section) {
        adapter.notifySectionFooterRemoved(section);
    }

    @Override
    public void onSectionFooterChanged(int section) {
        adapter.notifySectionFooterChanged(section);
    }
}
