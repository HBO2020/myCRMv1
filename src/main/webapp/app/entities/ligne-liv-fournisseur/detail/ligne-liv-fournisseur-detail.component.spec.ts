import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { LigneLivFournisseurDetailComponent } from './ligne-liv-fournisseur-detail.component';

describe('LigneLivFournisseur Management Detail Component', () => {
  let comp: LigneLivFournisseurDetailComponent;
  let fixture: ComponentFixture<LigneLivFournisseurDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [LigneLivFournisseurDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ ligneLivFournisseur: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(LigneLivFournisseurDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(LigneLivFournisseurDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load ligneLivFournisseur on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.ligneLivFournisseur).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
