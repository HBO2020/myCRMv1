import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { LigneCmdFournisseurDetailComponent } from './ligne-cmd-fournisseur-detail.component';

describe('LigneCmdFournisseur Management Detail Component', () => {
  let comp: LigneCmdFournisseurDetailComponent;
  let fixture: ComponentFixture<LigneCmdFournisseurDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [LigneCmdFournisseurDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ ligneCmdFournisseur: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(LigneCmdFournisseurDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(LigneCmdFournisseurDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load ligneCmdFournisseur on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.ligneCmdFournisseur).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
