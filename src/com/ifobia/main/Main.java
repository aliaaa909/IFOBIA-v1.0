/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ifobia.main;

/**
 *
 * @author Andhika Eka Pratama
 */
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.formdev.flatlaf.util.UIScale;
import com.ifobia.form.FormDashboard;
import com.ifobia.form.FormKelolaAbsensi;
import com.ifobia.form.FormKelolaJadwal;
import com.ifobia.form.FormKelolaPengumuman;
import com.ifobia.form.FormKelolaTugas;
import com.ifobia.form.FormMateri;
import com.ifobia.form.FormPengumuman;
import com.ifobia.form.FormTugas;
import com.ifobia.menu.Menu;
import com.ifobia.menu.MenuAction;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class Main extends JLayeredPane{
    private Menu menu;
    private JPanel panelBody;
    private JButton menuButton;

    public Main() {
        init();
    }

    private void init() {
        setBorder(new EmptyBorder(5,5,5,5));
        setLayout(new MainFormLayout());
         menu = new Menu();
        panelBody = new JPanel(new BorderLayout());
        initMenuArrowIcon();
        menuButton.putClientProperty(FlatClientProperties.STYLE, ""
        + "background:$Menu.button.background;"
        + "arc:999;"
        + "focusWidth:0;"
        + "borderWidth:0");

        menuButton.addActionListener((ActionEvent e) -> {
            setMenuFull(!menu.isMenuFull());
        });
        initMenuEvent();
        add(menuButton);
        add(menu);
        add(panelBody);
    }
    
     private void initMenuArrowIcon() {
    if(menuButton == null) {
        menuButton = new JButton();
        }
        String icon = (getComponentOrientation().isLeftToRight() ? "menu_left.svg" : "menu_right.svg");
        menuButton.setIcon(new FlatSVGIcon("com/ifobia/icon/" + icon, 0.8f));
    }
     
    private void initMenuEvent() {
         menu.addMenuEvent((int index, int subIndex, MenuAction action) -> {
        if (FormMenuUtama.isAdmin()) {
            // ADMIN MENU INDICES
            if(index == 0) {
                FormMenuUtama.showForm(new FormDashboard());
            }else if(index == 1) {
                FormMenuUtama.showForm(new FormMateri());
            }else if(index == 2) {
                FormMenuUtama.showForm(new FormTugas());
            }else if(index == 3) {
                FormMenuUtama.showForm(new FormPengumuman());
            }else if(index == 4) {
                FormMenuUtama.showForm(new FormKelolaJadwal());
            }else if(index == 5) {
                FormMenuUtama.showForm(new FormKelolaTugas());
            }else if(index == 6) {
                FormMenuUtama.showForm(new FormKelolaPengumuman());
            }else if(index == 7) {
                FormMenuUtama.showForm(new FormKelolaAbsensi());
            }else if(index == 8) {
                FormMenuUtama.logout();
            }else{
                action.cancel();
            }
        } else {
            // USER MENU INDICES
            if(index == 0) {
                FormMenuUtama.showForm(new FormDashboard());
            }else if(index == 1) {
                FormMenuUtama.showForm(new FormMateri());
            }else if(index == 2) {
                FormMenuUtama.showForm(new FormTugas());
            }else if(index == 3) {
                FormMenuUtama.showForm(new FormPengumuman());
            }else if(index == 4) {
                FormMenuUtama.logout();
            }else{
                action.cancel();
            }
        }
    });
    }   
     
     private void setMenuFull(boolean full) {
    String icon;
    if(getComponentOrientation().isLeftToRight()) {
            icon = (full) ? "menu_left.svg" : "menu_right.svg";
        }else{
            icon = (full) ? "menu_right.svg" : "menu_left.svg";
        }
        menuButton.setIcon(new FlatSVGIcon("com/ifobia/icon/" + icon, 0.8f));
        menu.setMenuFull(full);
        revalidate();
    }
     
     public void hideMenu() {
    menu.hideMenuItem();
    }
     
     public void showForm(Component component) {
    panelBody.removeAll();
    panelBody.add(component);
    panelBody.repaint();
    panelBody.revalidate();
    }
    
    public void setSelectedMenu(int index, int subIndex) {
    menu.setSelectedMenu(index, subIndex);
     }
     
    public void refreshMenu() {
    menu.refreshMenu();
    }
     
    private class MainFormLayout implements LayoutManager{

        @Override
        public void addLayoutComponent(String name, Component comp) {
            
        }

        @Override
        public void removeLayoutComponent(Component comp) {
            
        }

        @Override
        public Dimension preferredLayoutSize(Container parent) {
            synchronized (parent.getTreeLock()) {
            return new Dimension(5,5);
            }
        }

        @Override
        public Dimension minimumLayoutSize(Container parent) {
           synchronized (parent.getTreeLock()) {
            return new Dimension(0,0);
            }
        }

        @Override
        public void layoutContainer(Container parent) {
            synchronized (parent.getTreeLock()) {
                boolean ltr = parent.getComponentOrientation().isLeftToRight();
                Insets insets = UIScale.scale(parent.getInsets());
                int x = insets.left;
                int y = insets.top;
                int width = parent.getWidth() - (insets.left + insets.right);
                int height = parent.getHeight() - (insets.top + insets.bottom);
                int menuWidth = UIScale.scale(menu.isMenuFull() ? menu.getMenuMaxWidth() : menu.getMenuMinWidth());
                int menuX = ltr ? x : x + width - menuWidth;
                menu.setBounds(menuX, y, menuWidth, height);
                int menuButtonWidth = menuButton.getPreferredSize().width;
                int menuButtonHeight = menuButton.getPreferredSize().height;
                int menuBX;
                if (ltr) {
                    menuBX = (int) (x + menuWidth - (menuButtonWidth * (menu.isMenuFull() ? 0.5f : 0.3f)));
                } else {
                    menuBX = (int) (menuX - (menuButtonWidth * (menu.isMenuFull() ? 0.5f : 0.7f)));
                }
                menuButton.setBounds(menuBX, UIScale.scale(30), menuButtonWidth, menuButtonHeight);
                int gap = UIScale.scale(5);
                int bodyWidth = width - menuWidth - gap;
                int bodyHeight = height;
                int bodyX = ltr ? (x + menuWidth + gap) : x;
                int bodyy = y;
                panelBody.setBounds(bodyX, bodyy, bodyWidth, bodyHeight);
            }
            
        }
        
        

    }
    
}