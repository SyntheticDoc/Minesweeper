package com.gameobject;

import com.gameobject.enums.FIELDSTATE;

public class Field extends Gameasset {
    private FIELDSTATE state;
    private Playfield playfield;
    public boolean isMine = false;
    private String assetFile_normal;
    private String assetFile_depressed;
    private boolean depressed = false;
    private boolean depressable = false;

    public Field(String name, String assetFile, double posX, double posY, Playfield playfield) {
        super(name, assetFile, posX, posY, 25, 25);
        setState(FIELDSTATE.FIELDUNREVEALED);
        this.playfield = playfield;
    }

    public void depress(MouseClickWrapper click) {
        if(depressable && !depressed && click.buttonClicked == 1) {
            depressed = true;
            assetFile = assetFile_depressed;
        }
    }

    public void click(MouseClickWrapper click) {
        if(depressable && depressed) {
            depressed = false;
            assetFile = assetFile_normal;
        } else if (click != null && click.buttonClicked == 1 && state == FIELDSTATE.FIELDUNREVEALED) {
            setState(FIELDSTATE.FIELDFLAGGED);
        } else if(click != null && click.buttonClicked == 1 && state == FIELDSTATE.FIELDFLAGGED) {
            setState(FIELDSTATE.FIELDUNREVEALED);
        }
    }

    // returns 1 if a flag is set, -1 if a flag is deleted and 0 otherwise
    public int toggleFlag() {
        if(state == FIELDSTATE.FIELDUNREVEALED) {
            setState(FIELDSTATE.FIELDFLAGGED);
            return 1;
        } else if (state == FIELDSTATE.FIELDFLAGGED) {
            setState(FIELDSTATE.FIELDUNREVEALED);
            return -1;
        } else {
            return 0;
        }
    }

    public void release() {
        if(depressable && depressed) {
            depressed = false;
            assetFile = assetFile_normal;
        }
    }

    public void reveal() {
        if(state == FIELDSTATE.FIELDUNREVEALED || state == FIELDSTATE.FIELDFLAGGED) {
            if (isMine) {
                if(playfield.isGameOver) {
                    setState(FIELDSTATE.FIELDMINE);
                } else {
                    setState(FIELDSTATE.FIELDMINECLICKED);
                    playfield.gameOver();
                }
            } else if (state == FIELDSTATE.FIELDFLAGGED && playfield.isGameOver) {
                setState(FIELDSTATE.FIELDFlAGGEDWRONG);
            } else if(!playfield.isGameOver) {
                reveal_recursive();
            }
        }
    }

    private void reveal_recursive() {
        if(state != FIELDSTATE.FIELDUNREVEALED && state != FIELDSTATE.FIELDFLAGGED) {
            return;
        }

        Field[] fields = playfield.getSurroundingFields(this);
        int mineCounter = 0;

        for(Field f : fields) {
            if(f != null) {
                if(f.isMine) {
                    mineCounter++;
                }
            }
        }

        switch(mineCounter) {
            case 0:
                setState(FIELDSTATE.FIELDEMPTY);

                for(Field f : fields) {
                    if(f != null) {
                        if(!f.isMine) {
                            f.reveal_recursive();
                        }
                    }
                }
                break;
            case 1:
                setState(FIELDSTATE.FIELD1);
                break;
            case 2:
                setState(FIELDSTATE.FIELD2);
                break;
            case 3:
                setState(FIELDSTATE.FIELD3);
                break;
            case 4:
                setState(FIELDSTATE.FIELD4);
                break;
            case 5:
                setState(FIELDSTATE.FIELD5);
                break;
            case 6:
                setState(FIELDSTATE.FIELD6);
                break;
            case 7:
                setState(FIELDSTATE.FIELD7);
                break;
            case 8:
                setState(FIELDSTATE.FIELD8);
                break;
            default:
                throw new IllegalStateException("ERROR in Field.reveal_recursive(): Unknown mineCounter value " + mineCounter);
        }
    }

    public FIELDSTATE getState() {
        return state;
    }

    public void setState(FIELDSTATE state) {
        this.state = state;

        switch(state) {
            case FIELD1:
                assetFile = AssetHandler.field_1;
                depressable = false;
                break;
            case FIELD2:
                assetFile = AssetHandler.field_2;
                depressable = false;
                break;
            case FIELD3:
                assetFile = AssetHandler.field_3;
                depressable = false;
                break;
            case FIELD4:
                assetFile = AssetHandler.field_4;
                depressable = false;
                break;
            case FIELD5:
                assetFile = AssetHandler.field_5;
                depressable = false;
                break;
            case FIELD6:
                assetFile = AssetHandler.field_6;
                depressable = false;
                break;
            case FIELD7:
                assetFile = AssetHandler.field_7;
                depressable = false;
                break;
            case FIELD8:
                assetFile = AssetHandler.field_8;
                depressable = false;
                break;
            case FIELDEMPTY:
                assetFile = AssetHandler.field_empty;
                depressable = false;
                break;
            case FIELDMINE:
                assetFile = AssetHandler.field_mine;
                depressable = false;
                break;
            case FIELDMINECLICKED:
                assetFile = AssetHandler.field_mine_clicked;
                depressable = false;
                break;
            case FIELDUNREVEALED:
                assetFile = AssetHandler.field_unrevealed;
                assetFile_normal = AssetHandler.field_unrevealed;
                assetFile_depressed = AssetHandler.field_unrevealed_depressed;
                depressable = true;
                break;
            case FIELDUNREVEALEDDEPRESSED:
                assetFile = AssetHandler.field_unrevealed_depressed;
                assetFile_normal = AssetHandler.field_unrevealed;
                assetFile_depressed = AssetHandler.field_unrevealed_depressed;
                depressable = true;
                break;
            case FIELDFLAGGED:
                assetFile = AssetHandler.field_unrevealed_flagged;
                assetFile_normal = AssetHandler.field_unrevealed_flagged;
                assetFile_depressed = AssetHandler.field_unrevealed_flagged_depressed;
                depressable = true;
                break;
            case FIELDFLAGGEDDEPRESSED:
                assetFile = AssetHandler.field_unrevealed_flagged_depressed;
                assetFile_normal = AssetHandler.field_unrevealed_flagged;
                assetFile_depressed = AssetHandler.field_unrevealed_flagged_depressed;
                depressable = true;
                break;
            case FIELDFlAGGEDWRONG:
                assetFile = AssetHandler.field_unrevealed_flagged_wrong;
                depressable = false;
                break;
            default:
                throw new IllegalArgumentException("ERROR in Field.setState(): Unknown state " + state);
        }
    }

    @Override
    public boolean equals(Object o) {
        if(o == null || o.getClass() != this.getClass()) {
            return false;
        } else {
            Field obj = (Field) o;

            return obj.name.equals(this.name);
        }
    }

    @Override
    protected String enumerateAttributes() {
        return super.enumerateAttributes() + ",state=" + state;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
