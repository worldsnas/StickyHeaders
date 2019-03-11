package org.zakariya.stickyheaders;

public interface SectionListUpdateCallback {
    //items
    void onItemInserted(int section, int item, int count);
    void onItemRemoved(int section, int item, int count);
    void onItemChanged(int section, int item);

    //section
    void onSectionInserted(int section);
    void onSectionRemoved(int section, int item, int count);

    //footer
    void onSectionFooterInserted(int section);
    void onSectionFooterRemoved(int section);
    void onSectionFooterChanged(int section);
}
