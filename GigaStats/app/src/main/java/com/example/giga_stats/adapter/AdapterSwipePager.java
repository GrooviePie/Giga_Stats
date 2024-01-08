/**
 * Die Klasse AdapterSwipePager ist ein FragmentStateAdapter, der dazu dient, Fragmente innerhalb eines ViewPager2 in der Giga Stats-Anwendung zu verwalten.
 *
 * Diese Klasse erweitert FragmentStateAdapter und stellt die notwendigen Methoden bereit, um den Lebenszyklus von Fragmenten in einem ViewPager2 zu verwalten.
 *
 * @version 1.0
 */
package com.example.giga_stats.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.List;


public class AdapterSwipePager extends FragmentStateAdapter {

    private List<Fragment> fragments;

    /**
     * Konstruktor für den AdapterSwipePager.
     *
     * @param fragmentActivity Die FragmentActivity, zu der der Adapter gehört.
     * @param fragments        Liste der Fragmente, die vom Adapter verwaltet werden sollen.
     */
    public AdapterSwipePager(FragmentActivity fragmentActivity, List<Fragment> fragments) {
        super(fragmentActivity);
        this.fragments = fragments;
    }

    /**
     * Gibt die Gesamtanzahl der vom Adapter verwalteten Fragmente zurück.
     *
     * @return Die Gesamtanzahl der Fragmente.
     */
    @Override
    public int getItemCount() {
        return fragments.size();
    }

    /**
     * Erstellt und gibt ein Fragment für die angegebene Position zurück.
     *
     * @param position Die Position des Fragments im ViewPager2.
     * @return Das Fragment an der angegebenen Position.
     */
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return fragments.get(position);
    }
}

